package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.enums.SendState
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IComunication
import com.gsi.tm.interfaces.IEditTeamManagerContract
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.MOption
import com.gsi.tm.models.OperationTaskStatus
import com.gsi.tm.models.Person
import java.util.ArrayList

class AssignTeamManagerVPresenter(val context: Context) : IEditTeamManagerContract.Presenter {
    var mView: IEditTeamManagerContract.MView? = null
    var sender: IComunication? = null

    override fun onCreateView(mView: IEditTeamManagerContract.MView) {
        this.mView = mView
    }

    override fun updateState(gsiTask: GSITaskDescription) {
        val database = App.getManagerDB(context)
        database?.updateStateTask(gsiTask)
        mView?.updateState(gsiTask.stateTask)
    }

    /**
     * get task from Data Base
     */
    override fun getTask(idTask: Long): GSITaskDescription? {
        val database = App.getManagerDB(context)
        val optionSelect = MOption<String, String, Any>("id", value = idTask)
        val gsiTask = database?.getListTasks(where = arrayListOf(optionSelect))?.firstOrNull()
        return gsiTask
    }

    /**
     *  assignTask and send to server
     */
    override fun assignTask(
        listTaskDescriptionGSI: ArrayList<GSITaskDescription>,
        assignmentList: ArrayList<Person>
    ) {
        var resultConnect = false
        sender?.sendNewTasks(listTaskDescriptionGSI, assignmentList) { result, error ->
            val stateSend = if (result) {
                SendState.Success
            } else
                SendState.Failed

            listTaskDescriptionGSI.forEach { taskDescription ->
                val dbManager = App.getManagerDB(context)
                dbManager?.insert(taskDescription)
                val insertedTaskId = dbManager?.getLastId(taskDescription)?.toLong() ?: -1
                dbManager?.insert(
                    OperationTaskStatus(
                        -1,
                        stateSend,
                        date = taskDescription.date,
                        insertedTaskId
                    )
                )

                mView?.onAddTask(result, error)
            }
        } ?: run {
            mView?.onAddTask(resultConnect, "Sender is not work")
        }
    }


    override fun onDestroy() {
        this.mView = null
    }


}