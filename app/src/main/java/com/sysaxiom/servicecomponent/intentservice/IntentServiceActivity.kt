package com.sysaxiom.servicecomponent.intentservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intent_service.*
import androidx.core.content.ContextCompat
import android.content.Intent
import com.sysaxiom.servicecomponent.R


class IntentServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_service)

        start_service.setOnClickListener {
            val input = edit_text_input.getText().toString()
            val serviceIntent = Intent(this, MyIntentService::class.java)
            serviceIntent.putExtra("inputExtra", input)
            startService(serviceIntent)
            startService(serviceIntent)
        }
    }
}
