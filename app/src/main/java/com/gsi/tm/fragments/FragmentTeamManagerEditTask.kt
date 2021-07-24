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
import com.gsi.tm.interfaces.ContractTeamManagerGSI
import com.gsi.tm.interfaces.ContractTeamMemberEditGSI
import com.gsi.tm.presenters.FragmentTeamManagerViewPresenter
import kotlin.reflect.KClass

class FragmentTeamManagerEditTask : BaseFragment(), ContractTeamMemberEditGSI.MView {
    var rootView: View? = null
    //  val presenter: ContractTeamMemberEditGSI.Presenter? = null

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

    override fun goBack() {
        mnavigator?.goTo(FragmentGSITeamMember::class, null)
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {

    }

}
