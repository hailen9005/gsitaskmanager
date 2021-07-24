package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.enums.SendState
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IAddTaskViewPresentContract
import com.gsi.tm.interfaces.IComunication
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.OperationTaskStatus

class AddTaskViewFragmentPresenter(var context: Context) : IAddTaskViewPresentContract.Presenter {

    var sender: IComunication? = null
    var mView: IAddTaskViewPresentContract.MView? = null
    var idPerson = -1

    override fun onCreateView(mView: IAddTaskViewPresentContract.MView) {
        this.mView = mView
    }

    override fun onClickAddTask(taskDescription: GSITaskDescription) {
        val dbManager = App.getManagerDB(context)
        dbManager?.insert(taskDescription)
        sender?.sendNewTask(taskDescription, App.profileUser!!) { result, error ->
            val stateSend = if (result) {
                SendState.Success
            } else
                SendState.Failed

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
        } ?: mView?.onAddTask(false, "Sender is not work")
    }

    override fun showPickerDate() {
        val dateMillis = System.currentTimeMillis()
        mView?.onSelectedDate(dateMillis)
    }


    override fun onDestroy() {
        this.mView = null
    }


}
