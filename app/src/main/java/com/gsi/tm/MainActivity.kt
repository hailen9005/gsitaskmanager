/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.gsi.tm.helpers.App
import com.gsi.tm.helpers.App.getDrawableByState
import com.gsi.tm.helpers.FMService
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.helpers.WebConnect
import com.gsi.tm.interfaces.IMainActivViewPresentContract
import com.gsi.tm.models.Person
import com.gsi.tm.presenters.MainActivityViewPresenter
import org.json.JSONObject

class MainActivity : AppCompatActivity(), IMainActivViewPresentContract.Mview {

    lateinit var barOptions: ConstraintLayout
    lateinit var tvHome: TextView
    lateinit var imvBell: ImageView
    var mainPresenter: MainActivityViewPresenter? = null
    val TAG = "FireBaseOnGSI"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barOptions = findViewById(R.id.cly_head_rmt)
        tvHome = findViewById(R.id.tv_home)
        imvBell = findViewById(R.id.imv_bell)
        mainPresenter = MainActivityViewPresenter(applicationContext)
        mainPresenter?.setSupportFragManager(supportFragmentManager, R.id.fragment_container_view)
        mainPresenter?.onCreateView(this)

        mainPresenter?.loadHomeView()



        Log.d("newToken", "" + getPreferences(MODE_PRIVATE).getString("token", "??"))
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

    override fun notifyEvent(gsiTaskDescription: GSITaskDescription) {
        val resId = R.drawable.ic_baseline_notifications_24
        imvBell.setImageDrawable(AppCompatResources.getDrawable(this, resId))
    }

    override fun resetNotifyEvent() {
        val resId = R.drawable.ic_baseline_notifications_b_24
        imvBell.setImageDrawable(AppCompatResources.getDrawable(this, resId))
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




}