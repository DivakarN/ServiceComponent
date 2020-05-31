package com.sysaxiom.servicecomponent.jobintentservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat

class MyJobIntentService : JobIntentService() {

    companion object{
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, 101, work)
        }
    }

    override fun onCreate() {
        println("Oncreate called")
        super.onCreate()
    }

    override fun onHandleWork(intent: Intent) {

        val input = intent?.getStringExtra("inputExtra")
        for (i in 0..30) {
            if (isStopped) return
            println(input + " - " + i)
            Thread.sleep(1000)
        }
        displayNotification("My Service", input.toString())
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
            .setSmallIcon(com.sysaxiom.servicecomponent.R.drawable.ic_android)

        notificationManager.notify(1, notification.build())
    }

    override fun onDestroy() {
        println("Destroy Called")
        super.onDestroy()
    }

    override fun onStopCurrentWork(): Boolean {
        return super.onStopCurrentWork()
    }
}
