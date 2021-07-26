/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.fragments.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsi.tm.R
import com.gsi.tm.enums.StateTask
import com.gsi.tm.fragments.BaseFragment
import com.gsi.tm.interfaces.IEditTeamManagerContract
import kotlin.reflect.KClass

class EditTaskManagerFragment : BaseFragment(), IEditTeamManagerContract.MView {
    var rootView: View? = null
    //val presenter: ContractTeamMemberEditGSI.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        rootView = layoutInflater.inflate(R.layout.view_edit_task, null)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    override fun updateState(stateTask: StateTask) {

    }

    override fun onAddTask(result: Boolean, error: String?) {
        goBack()
    }

    override fun goBack() {
        mNavigator?.goTo(ManagerFragment::class, null)
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {

    }

}
