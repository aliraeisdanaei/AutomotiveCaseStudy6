package com.example.sensorsample

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*
import com.android.volley.Request
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class AppScreen(carContext: CarContext) : Screen(carContext) {
    companion object {
        var data: MutableList<String> = mutableListOf();
    }

    override fun onGetTemplate(): Template {

        val paneBuilder = Pane.Builder()
        for (line in data) {
            val row: Row = Row.Builder()
                .setTitle(line)
                .build()
            paneBuilder.addRow(row)
        }


        return PaneTemplate.Builder(
            paneBuilder.build()
        ).setHeaderAction(Action.APP_ICON).build()
    }
}