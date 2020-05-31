package com.sysaxiom.servicecomponent.jobintentservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sysaxiom.servicecomponent.R
import kotlinx.android.synthetic.main.activity_job_intent_service.*

class JobIntentServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_intent_service)

        start_service.setOnClickListener {
            val input = edit_text_input.getText().toString()
            val serviceIntent = Intent(this, MyJobIntentService::class.java)
            serviceIntent.putExtra("inputExtra", input)
            MyJobIntentService.enqueueWork(this,serviceIntent)
        }
    }
}
