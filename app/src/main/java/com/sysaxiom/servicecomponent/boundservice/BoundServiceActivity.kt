package com.sysaxiom.servicecomponent.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bound_service.*
import kotlinx.android.synthetic.main.activity_bound_service.edit_text_input
import kotlinx.android.synthetic.main.activity_bound_service.start_service
import kotlinx.android.synthetic.main.activity_bound_service.stop_service
import kotlinx.android.synthetic.main.activity_started_service.*

class BoundServiceActivity : AppCompatActivity() {

    private var isBound = false
    private var service: MyBoundService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sysaxiom.servicecomponent.R.layout.activity_bound_service)

        start_service.setOnClickListener {
            val input = edit_text_input.getText().toString()
            service?.display(input)
        }

        stop_service.setOnClickListener {
            if (isBound && service != null) {
                isBound = false
                unbindService(serviceConnection)
            }
        }

    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this, MyBoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound && service != null) {
            unbindService(serviceConnection)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val binder = iBinder as MyBoundService.LocalBinder
            service = binder.getService()

            isBound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            isBound = false
        }
    }

}
