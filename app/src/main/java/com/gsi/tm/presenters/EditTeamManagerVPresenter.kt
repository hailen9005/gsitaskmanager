package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IEditTeamManagerContract
import com.gsi.tm.interfaces.IEditTeamMemberContract
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.MOption

class EditTeamManagerVPresenter(val context: Context) : IEditTeamManagerContract.Presenter {
    var mview: IEditTeamManagerContract.MView? = null

    override fun onCreateView(mView: IEditTeamManagerContract.MView) {
        this.mview = mView
    }

    override fun updateState(gsiTask: GSITaskDescription) {
        val database = App.getManagerDB(context)
        database?.updateStateTask(gsiTask)
        mview?.updateState(gsiTask.stateTask)
    }

    override fun getTask(idTask: Long): GSITaskDescription? {
        val database = App.getManagerDB(context)
        val optionSelect = MOption<String, String, Any>("id", value = idTask)
        val gsiTask = database?.getListTasks(where = arrayListOf(optionSelect))?.firstOrNull()
        return gsiTask
    }


    override fun onDestroy() {
        this.mview = null
    }


}