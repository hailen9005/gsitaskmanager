package com.gsi.tm.helpers

import android.util.Log
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


}