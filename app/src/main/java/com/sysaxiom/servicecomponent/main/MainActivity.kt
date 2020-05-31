package com.sysaxiom.servicecomponent.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sysaxiom.servicecomponent.R
import com.sysaxiom.servicecomponent.boundservice.BoundServiceActivity
import com.sysaxiom.servicecomponent.foregroundservice.ForegroundServiceActivity
import com.sysaxiom.servicecomponent.intentservice.IntentServiceActivity
import com.sysaxiom.servicecomponent.jobintentservice.JobIntentServiceActivity
import com.sysaxiom.servicecomponent.startedservice.StartedServiceActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job_intent_service.setOnClickListener {
            Intent(this, JobIntentServiceActivity::class.java).also {
                this.startActivity(it)
            }
        }

        intent_service.setOnClickListener {
            Intent(this, IntentServiceActivity::class.java).also {
                this.startActivity(it)
            }
        }

        foreground_service.setOnClickListener {
            Intent(this, ForegroundServiceActivity::class.java).also {
                this.startActivity(it)
            }
        }

        started_service.setOnClickListener {
            Intent(this, StartedServiceActivity::class.java).also {
                this.startActivity(it)
            }
        }

        bound_service.setOnClickListener {
            Intent(this, BoundServiceActivity::class.java).also {
                this.startActivity(it)
            }
        }

    }
}
