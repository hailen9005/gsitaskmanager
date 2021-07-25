/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsi.tm.R
import com.gsi.tm.enums.StateTask
import com.gsi.tm.interfaces.IEditTeamManagerContract
import com.gsi.tm.interfaces.IEditTeamMemberContract
import com.gsi.tm.presenters.EditTeamMemberVPresenter
import kotlin.reflect.KClass

class EditTaskTeamManagerFragment : BaseFragment(), IEditTeamManagerContract.MView {
    var rootView: View? = null
    val presenter: EditTeamMemberVPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        rootView = layoutInflater.inflate(R.layout.form_task_add, null)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    override fun updateState(stateTask: StateTask) {

    }

    override fun goBack() {
        mNavigator?.goTo(TeamManagerFragment::class, null)
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {

    }

}
