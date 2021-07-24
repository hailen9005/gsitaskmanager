package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.enums.ListOption
import com.gsi.tm.fragments.FragmentTeamMemberEditTask
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.ContractTeamManagerGSI
import com.gsi.tm.interfaces.ContractTeamMemberEditGSI
import com.gsi.tm.interfaces.INavigate
import com.gsi.tm.models.GSITaskDescription

class FmEditTeamMemberVP(val context: Context) : ContractTeamMemberEditGSI.Presenter {
    var view: ContractTeamMemberEditGSI.MView? = null

    override fun onCreateView(mView: ContractTeamMemberEditGSI.MView) {
        this.view = mView
    }

    override fun updateState(gsiTask: GSITaskDescription) {
        val database = App.getManagerDB(context)
        database?.updateStateTask(gsiTask)
        view?.updateState(gsiTask.stateTask)
    }

    override fun getTask(idTask: Long): GSITaskDescription? {
        val database = App.getManagerDB(context)
        val gsiTask = database?.getListTasks(idTask = idTask.toString())?.firstOrNull()
        return gsiTask
    }


    override fun onDestroy() {
        this.view = null
    }


}