package com.example.collisiondetectionsample

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Delivery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.concurrent.thread

class AppSession : Session() {
    val uri = "10.0.2.2"
    val queue = NetworkQueue.getInstance(carContext)

    val connectionFactory = ConnectionFactory()
    private lateinit var channel: Channel

    override fun onCreateScreen(intent: Intent): Screen {
        val screen = AppScreen(carContext)

        MainScope().launch {
            withContext(Dispatchers.IO) {
                connectionFactory.host = uri
                connectionFactory.port = 5672
                val connection = connectionFactory.newConnection()
                channel = connection.createChannel()

                val q = channel.queueDeclare("obstacle_detected", false, false, false, null)
                channel.basicConsume("obstacle_detected", true, deliverCallback(screen)) { _ -> {} }
            }
        }
        return screen
    }

    val deliverCallback: (AppScreen) -> (String, Delivery) -> Unit = { screen ->
        { consumerTag: String, delivery: Delivery ->
            val data = String(delivery.body, Charsets.UTF_8)
            AppScreen.data = data
            screen.invalidate()
        }
    }

    fun updateLoop(screen: AppScreen) {
        screen.invalidate()
        Thread.sleep(5_000)
    }

    fun requestLoop(screen: AppScreen) {
        val respListener = {response: JSONObject ->
            AppScreen.data = formatData(response)
            screen.invalidate()
            }
        val errorListener = {error: VolleyError -> AppScreen.data = error.toString() }
        while (true) {
            val request = JsonObjectRequest(Request.Method.GET, uri, null,
                respListener,
                errorListener
            )
            queue.addToRequestQueue(request)
            Thread.sleep(5_000)
        }
    }

    fun formatData(data: JSONObject): String {
        if (data.length() == 0) {
            return "No obstacles ahead."
        }
        val obstacle_type = data["obstacle_type"]
        val obstacle_distance = data["distance"]
        return "Detected $obstacle_type ${obstacle_distance}m ahead."
    }
}