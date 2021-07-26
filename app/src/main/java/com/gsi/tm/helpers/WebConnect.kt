package com.gsi.tm.helpers

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object WebConnect {

    val TAG = "GSI-WebConnect"
    fun connect(strJSON: String, function: (success: Boolean, error: String?) -> Unit) {
        var result = false
        var error = ""
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
                result = true
            } else {
                error = "Error Response" + httpURLConnection.responseCode
                Log.e(TAG, "Error Response" + httpURLConnection.responseCode)
            }
        } catch (ex: Exception) {
            error = "Error Exception ${ex.message}"
            Log.e(TAG, "Exception ${ex.message}")

        } finally {
            function.invoke(result, error)
        }
    }


    fun subscribe(topic: String, fn: (result: Boolean) -> Unit) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnSuccessListener {
            Log.d(TAG, "task subscription")
        }
        FirebaseMessaging.getInstance().subscribeToTopic(topic).addOnCompleteListener { task ->
            var msg = " addOnCompleteListener ok"
            if (!task.isSuccessful) {
                msg = "addOnCompleteListener failed  "
            }
            Log.d(TAG, msg.plus(" topic: $topic"))
            fn.invoke(task.isSuccessful)
        }
    }

    fun unsubscribe(topic: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener {
            Log.d(TAG, "task subscription")
        }

    }


    fun contructMessage(
        title: String,
        content: String,
        topic: String,
        list: ArrayList<Pair<String, String>>
    ): String {
        // val jsonArray = JSONArray(listOf(100,101, 102, 103,104))
        //JSONObject().put("aio",jsonArray)

        val body = JSONObject()
        val data = JSONObject()
        data.put("title", title)
        data.put("content", content)

        list.forEach {
            data.put(it.first, it.second)
        }
        body.put("data", data)
        body.put("to", "/topics/$topic")
        return body.toString()
    }

}