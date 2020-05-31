package com.sysaxiom.servicecomponent.startedservice

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.sysaxiom.servicecomponent.R

class MyStartedService : Service() {

    override fun onCreate() {
        println("Oncreate called")
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")
        for (i in 0..30) {
            println(input + " - " + i)
            Thread.sleep(1000)
        }
        displayNotification("My Service", input.toString())
        return START_NOT_STICKY
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
        println("Destroy Called")
        super.onDestroy()
    }
}
