package com.example.collisiondetectionsample

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*
import com.android.volley.Request
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class AppScreen(carContext: CarContext) : Screen(carContext) {
    companion object {
        var data = "No obstacles ahead.";
    }

    override fun onGetTemplate(): Template {
        val row: Row = Row.Builder()
            .setTitle(data)
            .build()

        return PaneTemplate.Builder(
            Pane.Builder()
                .addRow(row)
                .build()
        ).setHeaderAction(Action.APP_ICON).build()
    }
}