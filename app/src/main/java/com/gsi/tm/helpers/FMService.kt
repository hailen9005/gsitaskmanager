package com.gsi.tm.helpers

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


class FMService : FirebaseMessagingService() {

    val TAG = "FM-GSI"

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        //com.google.firebase.messaging.RemoteMessage()

    }



    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        getSharedPreferences("token", MODE_PRIVATE).edit().putString("token", p0).apply();
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

            val messageId = remoteMessage.messageId
            val senderId = remoteMessage.senderId
            val title = remoteMessage.data["title"]
            val content = remoteMessage.data["content"]
            val idDev = remoteMessage.data["registration_ids"]
            Log.d(TAG, "onMessageReceived: $title $content $idDev  $senderId : $messageId ")
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }




}