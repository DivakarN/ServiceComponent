# ServiceComponent

Services:
---------

A Service is an android component that perform long running operations in the background.
Services are categorised into two.
1. Started Service
2. Bound Service.

onStartCommand() - The system calls this method when another component, such as an activity, requests that the service be started, by calling startService(). If you implement this method, it is your responsibility to stop the service when its work is done, by calling stopSelf() or stopService() methods.

onBind() - The system calls this method when another component wants to bind with the service by calling bindService(). If you implement this method, you must provide an interface that clients use to communicate with the service, by returning an IBinder object. You must always implement this method, but if you don't want to allow binding, then you should return null.

onUnbind() - The system calls this method when all clients have disconnected from a particular interface published by the service.

onRebind() - The system calls this method when new clients have connected to the service, after it had previously been notified that all had disconnected in its onUnbind(Intent).

onCreate() - The system calls this method when the service is first created using onStartCommand() or onBind(). This call is required to perform one-time set-up.

onDestroy() - The system calls this method when the service is no longer used and is being destroyed. Your service should implement this to clean up any resources such as threads, registered listeners, receivers, etc.

Starting a Service﻿ Running on System Startup :
----------------------------------------------

Given the background nature of services, it is not uncommon for a service to need to be started when an Android-based system first boots up.
This can be achieved by creating a broadcast receiver with an intent filter configured to listen for the system android.intent.action.BOOT_COMPLETED﻿ intent.
When such an intent is detected, the broadcast receiver would simply invoke the necessary service and then return.
Note that, in order to function, such a broadcast receiver will need to request the android.permission.RECEIVE_BOOT_COMPLETED permission.

Started Service:
----------------

Started services are launched by other application components (such as an activity or even a broadcast receiver) and potentially run indefinitely in the background until the service is stopped, or is destroyed by the Android runtime system in order to free up resources.
Started services do not inherently provide a mechanism for interaction or data exchange with other components.
A service will continue to run if the application that started it is no longer in the foreground, and even in the event that the component that originally started the service is destroyed.
By default, a service will run within the same main thread as the application process from which it was launched.
It is important, therefore, that any CPU intensive tasks be performed in a new thread within the service. Instructing a service to run within a separate process (and therefore known as a remote service) requires a configuration change within the manifest file.
Started services are launched via a call to the startService() method﻿, passing through as an argument an Intent object identifying the service to be started. When a started service has completed its tasks, it should stop itself via a call to stopSelf().﻿ Alternatively, a running service may be stopped by another component via a call to the stopService() method﻿, passing through as an argument the matching Intent for the service to be stopped.
Depending if we want to restart the service if the system kills it, we return either START_STICKY, START_NOT_STICKY or START_REDELIVER_INTENT from onStartCommand.

START_STICKY - START_STICKY tells the OS to recreate the service after it has enough memory and call onStartCommand() again with a null intent.
START_NOT_STICKY - START_NOT_STICKY tells the OS to not bother recreating the service again.
START_REDELIVER_INTENT - START_REDELIVER_INTENT that tells the OS to recreate the service and redeliver the same intent to onStartCommand().

We stop the service by calling either stopSelf from within, or stopService from another app component.

//Started Service Implementation
class MyStartedService : Service() {

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")

        for (i in 0..30) {
            println(input + " - " + i)
            Thread.sleep(1000)
        }

        return START_NOT_STICKY
    }

}

//Starting started service
val input = edit_text_input.getText().toString()
val serviceIntent = Intent(this, MyStartedService::class.java)
serviceIntent.putExtra("inputExtra", input)
startService(serviceIntent)

Drawbacks:
Its run on main thread.

Bound Service:
--------------

A bound service allows the launching component to interact with, and receive results from, the service.
A component starts and binds to a bound service via a call to the bindService() method﻿.
When the service binding is no longer required by a client, a call should be made to the unbindService() method﻿.
When the last bound client unbinds from a service, the service will be terminated by the Android runtime system.
It is important to keep in mind that a bound service may also be started via a call to startService(). Once started, components may then bind to it via bindService() calls. When a bound service is launched via a call to startService() it will continue to run even after the last client unbinds from it.
A bound service must include an implementation of the onBind() method which is called both when the service is initially created and when other clients subsequently bind to the running service. The purpose of this method is to return to binding clients an object of type IBinder﻿ containing the information needed by the client to communicate with the service.

OnBind & LocalBinder:
---------------------

//Onbind returns Binder instance
override fun onBind(intent: Intent): IBinder {
	return binder
}

//It returns binder instance which will be used to interact between service and component.
inner class LocalBinder : Binder() {
    fun getService() : MyBoundService {
        return this@MyBoundService
    }
}

ServiceConnection:
------------------

Interface for monitoring the state of an application service.
ServiceConnection has two override methods which gives the service state details.

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

//Binding Service
val intent = Intent(this, MyBoundService::class.java)
bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

The first parameter of bindService() is an Intent that explicitly names the service to bind.
The second parameter is the ServiceConnection object.
The third parameter is a flag indicating options for the binding. It should usually be BIND_AUTO_CREATE in order to create the service if its not already alive. Other possible values are BIND_DEBUG_UNBIND and BIND_NOT_FOREGROUND, or 0 for none.

//Unbinding service
unbindService(serviceConnection)

Drawbacks:
Its run on main thread.

Foreground Service:
-------------------

Foreground Service runs independently from other app components (like activities), but displays a persistent notification to the user as long as it is running.
We create our foreground service by extending the Service class and overriding onStartCommand, where we then promote our service to the foreground by calling startForeground and passing a notification to it, which we build with the NotificationCompat.Builder.
Depending if we want to restart the service if the system kills it, we return either START_STICKY, START_NOT_STICKY or START_REDELIVER_INTENT from onStartCommand.

START_STICKY - START_STICKY tells the OS to recreate the service after it has enough memory and call onStartCommand() again with a null intent.
START_NOT_STICKY - START_NOT_STICKY tells the OS to not bother recreating the service again.
START_REDELIVER_INTENT - START_REDELIVER_INTENT that tells the OS to recreate the service and redeliver the same intent to onStartCommand().

We stop the service by calling either stopSelf from within, or stopService from another app component.

startForeground(id, notification):
----------------------------------

It creates a notification to informs users about the background processing. Its doesn't stop the service(After android oreo, service can be destroyed if the app is in the background after 1 minute).

//Foreground Service Implementation
class MyForegroundService : Service() {

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputExtra")

        for (i in 0..30) {
            println(input + " - " + i)
            Thread.sleep(1000)
        }

		val notification = NotificationCompat.Builder(applicationContext, "channelID")
            .setContentTitle(title)
            .setContentText(task)
            .setSmallIcon(R.drawable.ic_android)
            .build()

        startForeground(1,notification)

        return START_NOT_STICKY
    }

}

ContextCompat.startForegroundService(context, serviceIntent):
-----------------------------------------------

It calls startService() from API level 25 and below and calls startForegroundService from API level 26 and above.
Its checks if service has startForeground() in the time frame of 5 sec, if it finds then service will not be destroyed, otherwise service gets destroyed.

//Starting foreground service
val input = edit_text_input.getText().toString()
val serviceIntent = Intent(this, MyForegroundService::class.java)
serviceIntent.putExtra("inputExtra", input)
ContextCompat.startForegroundService(this,serviceIntent)

Drawbacks:
Its run on main thread.

Intent Service:
---------------

An IntentService is a subclass of service that runs in the background independently from an activity and handles all the incoming work on a HandlerThread(background thread).

onHandleIntent():
-----------------

In onHandleIntent, all the incoming intents are executed on a worker thread sequentially, one after another. When an intent is processed, the next one is started automatically.
Here we can do long-running work synchronously without blocking the main thread.
We can send data to the service in form of intent extras and when the last intent is finished, the service stops itself and onDestroy is triggered. This means that we don’t have to call stopSelf or stopService.

//IntentService Implementation
class MyIntentService : IntentService(){

    override fun onHandleIntent(intent: Intent?) {

        val input = intent?.getStringExtra("inputExtra")
        for (i in 0..49) {
            println(input + " - " + i)
            Thread.sleep(1000)
        }

    }

}

//Starting IntentService
val input = edit_text_input.getText().toString()
val serviceIntent = Intent(this, MyIntentService::class.java)
serviceIntent.putExtra("inputExtra", input)
startService(serviceIntent)
startService(serviceIntent)//We can add multiple intents and each will be executed sequentially one after one.

Drawbacks:
----------

If the app is killed or the service crashes, your intent will not start and your data will be lost.
Redelivering intent is quite mystery in IntentService

JobIntentService:
-----------------

The JobIntentService combines 2 different types of services: the IntentService and the JobService.

Since Android Oreo (API 26), background services can’t keep running while the app itself is in the background.
Instead, the system will kill them after around 1 minute or throw an IllegalStateException if we try to call startService from the background.
Since IntentService is a subclass of Service, it is affected by these background execution limits.
The recommended alternative for the IntentService is the JobIntentService, which starts a normal IntentService on API 25 and lower, but on Android Oreo and higher it uses the JobScheduler to schedule a JobService with setOverrideDeadline(0) instead.

when it finishes executing all the work, it automatically stops itself and onDestroy is called.

We don’t have to acquire a wake lock manually in the JobIntentService, since the superclass takes care of this. For this reason, we have to add the WAKE_LOCK permission into the manifest file.

In onStopCurrentWork, we can decide if we want to cancel a job and drop the current and all following intents if the system interrupts it, or if we want to reschedule the job with the last intent.
In both cases, we should stop the currently running work by checking if isStopped returns true, because otherwise, the system will ultimately kill the service.

We start the JobIntentService with the static enqueueWork method, where we have to pass a context, the JobIntentService class we want to start, a unique jobId and the service intent.

Depending on the API level, The system will start an IntentService immediately, or use the JobScheduler to enqueue a job, which requires the BIND_JOB_SERVICE permission, that we have to add to the service tag in the AndroidManifest.xml file.

//Enqueue work
companion object{
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, 101 //jobId, work)
        }
    }


//JobIntentService Implementation
class MyJobIntentService : JobIntentService() {

	//It will handle work
	override fun onHandleWork(intent: Intent) {

        val input = intent?.getStringExtra("inputExtra")
        for (i in 0..30) {
            if (isStopped) return // To stop the remaining execution
            println(input + " - " + i)
            Thread.sleep(1000)
        }
    }

	//It returns true if work has been stopped by system
	override fun onStopCurrentWork(): Boolean {
        return super.onStopCurrentWork()
    }

}


//Starting JobIntentService
val input = edit_text_input.getText().toString()
val serviceIntent = Intent(this, MyJobIntentService::class.java)
serviceIntent.putExtra("inputExtra", input)
MyJobIntentService.enqueueWork(this,serviceIntent) // Calling enqueueWork method to start the work

IntentService vs JobIntentService:
----------------------------------

IntentService - Drawback: The job given to the IntentService would get lost when the application is killed

JobIntentService - Job intent service is very similar to IntentService but with few benefits like the application can kill this job at any time and it can start the job from the beginning once the application gets recreated/up.
