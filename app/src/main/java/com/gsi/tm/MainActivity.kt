/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.gsi.tm.helpers.App
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.helpers.WebConnect
import com.gsi.tm.interfaces.IMainActivViewPresentContract
import com.gsi.tm.models.Person
import com.gsi.tm.presenters.MainActivityViewPresenter
import org.json.JSONObject

class MainActivity : AppCompatActivity(), IMainActivViewPresentContract.Mview {

    lateinit var barOptions: ConstraintLayout
    lateinit var tvHome: TextView
    var mainPresenter: MainActivityViewPresenter? = null
    val TAG = "FireBaseOnGSI"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barOptions = findViewById(R.id.cly_head_rmt)
        tvHome = findViewById(R.id.tv_home)
        mainPresenter = MainActivityViewPresenter(applicationContext)
        mainPresenter?.setSupportFragManager(supportFragmentManager, R.id.fragment_container_view)
        mainPresenter?.onCreateView(this)

        //  startService(Intent(this, FMService::class.java))
        val strJSON = contructMessage("sampletask", "any content")

        Thread(Runnable {
            WebConnect.connect(strJSON)
        }).start()

        mainPresenter?.loadHomeView()
    }


    val topic = App.TOPIC
    fun contructMessage(tasktitle: String, taskcontent: String): String {
        // val jsonArray = JSONArray(listOf(100,101, 102, 103,104))
        //JSONObject().put("aio",jsonArray)
        val body = JSONObject()
        val data = JSONObject()
        data.put("title", tasktitle)
        data.put("content", taskcontent)
        body.put("data", data)
        body.put("to", "/topics/$topic")
        return body.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter?.onDestroy()
    }

    override fun onBackPressed() {
        mainPresenter?.goBack()
    }

    override fun exit() {
        super.onBackPressed()
    }

    override fun onSendMessage() {

    }

    override fun onReceiveMessage(message: GSITaskDescription) {

    }

    override fun onSelectedUser(person: Person) {

    }


    override fun enableBarStatus(visible: Boolean) {
        if (visible) {
            val fullName = App.profileUser?.fullName
            val typeProfile = App.profileUser?.typeProfile
            val messageHome = "$typeProfile/$fullName"
            tvHome.text = messageHome
            barOptions.visibility = View.VISIBLE
        } else {
            barOptions.visibility = View.GONE

            App.profileUser = null
        }
    }


    fun subscribe() {
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

    fun unsubscribe() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic).addOnSuccessListener {
            Log.d(TAG, "task subscription")
        }

    }

}