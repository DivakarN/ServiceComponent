package com.sysaxiom.servicecomponent.boundservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.sysaxiom.servicecomponent.R

class MyBoundService : Service() {

    private val binder = LocalBinder()

    override fun onCreate() {
        println("Oncreate Called")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService() : MyBoundService {
            return this@MyBoundService
        }
    }

    fun display(input : String) {
        for (i in 0..30) {
            println("input - $i")
            Thread.sleep(1000)
        }
        displayNotification("My Service", input)
    }

    private fun displayNotification(title: String, task: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channelID",
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "channelID")
            .setContentTitle(title)
            .setContentText(task)
            .setSmallIcon(R.drawable.ic_android)

        notificationManager.notify(1, notification.build())
    }

    override fun onDestroy() {
        println("OnDestroy called")
        super.onDestroy()
    }

}
