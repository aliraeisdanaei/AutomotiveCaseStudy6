package com.example.sensorsample

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.coroutines.*
import org.json.JSONObject

class AppSession : Session() {
    val url = "http://10.0.2.2:5000/sensor"
    val queue = NetworkQueue.getInstance(carContext)

    override fun onCreateScreen(intent: Intent): Screen {
        val screen = AppScreen(carContext)
        MainScope().launch {
            withContext(Dispatchers.IO) {
                requestLoop(screen)
            }
        }
        return screen
    }

    fun requestLoop(screen: AppScreen) {
        val respListener = {response: JSONObject ->
            setData(response)
            screen.invalidate()
            }
        val errorListener = {error: VolleyError -> AppScreen.data = mutableListOf(error.toString()) }
        while (true) {
            val request = JsonObjectRequest(Request.Method.GET, url, null,
                respListener,
                errorListener
            )
            queue.addToRequestQueue(request)
            Thread.sleep(5_000)
        }
    }

    private fun setData(data: JSONObject): String {
        AppScreen.data.clear();
        var s = ""
        for (key in data.keys()) {
            val entry = data.getString(key)
            AppScreen.data.add("$key: $entry")
        }
        return s
    }
}