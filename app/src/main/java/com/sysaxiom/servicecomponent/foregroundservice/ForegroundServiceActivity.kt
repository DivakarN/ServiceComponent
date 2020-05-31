package com.sysaxiom.servicecomponent.foregroundservice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_foreground_service.*

class ForegroundServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sysaxiom.servicecomponent.R.layout.activity_foreground_service)

        start_service.setOnClickListener {
            val input = edit_text_input.getText().toString()
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            serviceIntent.putExtra("inputExtra", input)
            ContextCompat.startForegroundService(this,serviceIntent)
        }

        stop_service.setOnClickListener {
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            stopService(serviceIntent)
        }
    }
}
