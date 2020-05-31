package com.sysaxiom.servicecomponent.startedservice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sysaxiom.servicecomponent.R
import kotlinx.android.synthetic.main.activity_started_service.*

class StartedServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_started_service)

        start_service.setOnClickListener {
            val input = edit_text_input.getText().toString()
            val serviceIntent = Intent(this, MyStartedService::class.java)
            serviceIntent.putExtra("inputExtra", input)
            startService(serviceIntent)
        }

        stop_service.setOnClickListener {
            val serviceIntent = Intent(this, MyStartedService::class.java)
            stopService(serviceIntent)
        }

    }
}
