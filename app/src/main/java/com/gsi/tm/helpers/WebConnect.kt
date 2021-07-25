package com.gsi.tm.helpers

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object WebConnect {

    val TAG = "GSI-WebConnect"
    fun connect(strJSON: String) {
        try {
            val url = URL(App.FIREBASE_SERVER)
            val urlConex = url.openConnection()
            val httpURLConnection = urlConex as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true
            httpURLConnection.readTimeout = 15000
            httpURLConnection.connectTimeout = 10000
            httpURLConnection.setRequestProperty("authorization", "key=${App.SERVER_KEY}")
            httpURLConnection.setRequestProperty("Content-Type", "application/json")
            httpURLConnection.connect()

            val outputStream = httpURLConnection.outputStream
            val byteArray = strJSON.toByteArray()
            outputStream.write(byteArray, 0, byteArray.size)

            if (httpURLConnection.responseCode == 200) {
                Log.e(TAG, "Success notification sent")
            } else {
                Log.e(TAG, "Error Response" + httpURLConnection.responseCode)
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Exception ${ex.message}")

        } finally {
        }
    }


    fun subscribe(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener {
            Log.d(TAG, "task subscription")
        }
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnCompleteListener { task ->
            var msg = " addOnCompleteListener ok"
            if (!task.isSuccessful) {
                msg = "addOnCompleteListener failed  "
            }
            Log.d(TAG, msg)

        }
    }

    fun unsubscribe(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener {
            Log.d(TAG, "task subscription")
        }

    }

    val topic = App.TOPIC
    fun contructMessage(title: String, content: String): String {
        // val jsonArray = JSONArray(listOf(100,101, 102, 103,104))
        //JSONObject().put("aio",jsonArray)
        val body = JSONObject()
        val data = JSONObject()
        data.put("title", title)
        data.put("content", content)
        body.put("data", data)
        body.put("to", "/topics/$topic")
        return body.toString()
    }

}