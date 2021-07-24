package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.ContractAddTaskVP
import com.gsi.tm.models.GSITaskDescription

class FragmentAddTaskViewPresenter(var context: Context) : ContractAddTaskVP.Presenter {


    var mView: ContractAddTaskVP.MView? = null

    override fun onCreateView(mView: ContractAddTaskVP.MView) {
        this.mView = mView
    }

    override fun onClickAddTask(taskDescription: GSITaskDescription) {
        App.getManagerDB(context)?.insert(taskDescription)
        mView?.onAddTask()
    }

    override fun showPickerDate() {
        val dateMillis = System.currentTimeMillis()
        mView?.onSelectedDate(dateMillis)
    }

    override fun getAutor(context: Context?): String {
        var author = "unknow"
        context?.let { it ->

            val idPerson = (0).toString() //todo ojo

            App.getManagerDB(it)?.getListPersons(idPerson)?.let { list ->
                if (list.size > 0)
                    author = list[0].fullName
            }
        }
        return author
    }


    override fun onDestroy() {
        this.mView = null
    }


}
