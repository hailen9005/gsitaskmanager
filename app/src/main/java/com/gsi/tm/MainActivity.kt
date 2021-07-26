/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gsi.tm.helpers.App
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.interfaces.IMainActivViewPresentContract
import com.gsi.tm.models.Person
import com.gsi.tm.presenters.MainActivityViewPresenter
import java.util.*

class MainActivity : AppCompatActivity(), IMainActivViewPresentContract.Mview {

    private val REQUEST_PERMISSIONS: Int = 475
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

        checkPermission()
        mainPresenter?.loadHomeView()
        checkPermission()
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
            barOptions.visibility = View.INVISIBLE

            App.profileUser = null
        }
    }


    fun checkPermission() {
        val nogranted = arrayListOf<String>()
        var canInit = true
        val permissionsRequired =
            arrayListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
        permissionsRequired.forEach { permiss ->
            if (ContextCompat.checkSelfPermission(
                    this,
                    permiss
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                nogranted.add(permiss)
                if (permiss == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    canInit = false
                }
            }
        }
        //request permission
        if (nogranted.size > 0) {
            ActivityCompat.requestPermissions(this, nogranted.toTypedArray(), REQUEST_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            var noGranted: String? = null
            for (i in grantResults.iterator().withIndex()) {
                if ((permissions[i.index] == Manifest.permission.WRITE_EXTERNAL_STORAGE) and (i.value != PackageManager.PERMISSION_GRANTED)) {
                    noGranted = "${permissions[i.index].toLowerCase(Locale.getDefault())} \n"
                }
                var permiss_messg: String? = null
                noGranted?.let { permiss_messg += it }
                permiss_messg?.let {
                } ?: run {
                    //
                }
            }
        }
    }
}