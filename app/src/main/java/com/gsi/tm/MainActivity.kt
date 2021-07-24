/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.helpers.FMService
import com.gsi.tm.interfaces.ContractMainActivViewPresent
import com.gsi.tm.presenters.MainActivityViewPresenter
import layout.FragmentChooseProfile
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity(), ContractMainActivViewPresent.Mview {

    var mainPresenter: MainActivityViewPresenter = MainActivityViewPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter.setSupportFragManager(supportFragmentManager, R.id.fragment_container_view)
        mainPresenter.onCreateView(this)
        mainPresenter.goTo(FragmentChooseProfile::class, null)
        //  startService(Intent(this, FMService::class.java))

    }


    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onDestroy()
    }

    override fun onBackPressed() {
        mainPresenter.goBack()
    }

    override fun exit() {
        super.onBackPressed()
    }

    override fun onSendMessage() {

    }

    override fun onReceiveMessage(message: GSITaskDescription) {

    }


    override fun enableBarStatus(visible: Boolean) {
        findViewById<ConstraintLayout>(R.id.cly_head_rmt).visibility =
            if (visible) View.VISIBLE else View.GONE
    }


}