package com.gsi.tm.helpers

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


class FMService : FirebaseMessagingService() {

    val TAG = "FM-GSI"
    val topic = App.TOPIC

    override fun onCreate() {
        super.onCreate()

        subscribe()

        val strJSON = contructMessage("sampletask", "any content")

        Thread(Runnable {
            WebConnect.connect(strJSON)
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()

        unsubscribe()
    }

    fun contructMessage(tasktitle: String, taskcontent: String): String {
        val body = JSONObject()
        val data = JSONObject()
        data.put("title", tasktitle)
        data.put("content", taskcontent)
        body.put("data", data)
        body.put("to", "/topics/$topic")
        return body.toString()
    }


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.e(TAG, "new token $p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            if ( /* Check if data needs to be processed by long running job */true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //   scheduleJob()
            } else {
                // Handle message within 10 seconds
                //  handleNow()
            }

            val title = remoteMessage.data["title"]
            val content = remoteMessage.data["content"]
            Log.d(TAG, "$title $content")
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    fun subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener {
            Log.d(TAG, "task subscription")
        }
        FirebaseMessaging.getInstance().subscribeToTopic("topic")
            .addOnCompleteListener { task ->
                var msg = " addOnCompleteListener ok"
                if (!task.isSuccessful) {
                    msg = "addOnCompleteListener failed  "
                }
                Log.d(TAG, msg)

            }
    }

    fun unsubscribe() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener {
            Log.d(TAG, "task subscription")
        }

    }


}