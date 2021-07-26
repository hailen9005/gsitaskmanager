package com.gsi.tm.helpers

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.gsi.tm.models.MOption
import com.gsi.tm.models.Person
import com.gsi.tm.models.TokenDevices
import org.json.JSONObject


class FMService : FirebaseMessagingService() {

    val TAG = "FM-GSI"

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        getSharedPreferences("token", MODE_PRIVATE).edit().putString("token", p0).apply()
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

            insertOnDB(title, content, remoteMessage.data)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private fun insertOnDB(title: String?, content: String?, mdata: MutableMap<String, String>) {
        if ((title == "token") and (content?.contains("register") == true)) {
            val data = JSONObject(mdata as Map<*, *>)
            val fullName = data["fullName"] as String
            val occupation = data["occupation"] as String
            val globalId = data["globalId"] as String
            val typeProfile = data["typeProfile"] as String
            val token = data["token"] as String

            val isAccountLocal =
                App.getManagerDB(baseContext)?.getListPersons(
                    arrayListOf(
                        MOption(
                            "globalId",
                            value = globalId
                        )
                    )
                )?.firstOrNull()?.isAccountLocal ?: false

            if (!isAccountLocal) { //already
                val person = Person(
                    fullName = fullName,
                    occupation = occupation,
                    globalId = globalId,
                    isAccountLocal = isAccountLocal,
                    typeProfile = typeProfile
                )
                App.getManagerDB(baseContext)?.insert(person)
            }
            val tokenDevice = TokenDevices(-1, token, globalId)
            App.getManagerDB(baseContext)?.insert(tokenDevice)


        }
    }


}