package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.enums.ListOption
import com.gsi.tm.enums.StateTask
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IListTaskContract
import com.gsi.tm.models.*

class ListTaskFragmentViewPresenter(val context: Context) : IListTaskContract.Presenter {
    var view: IListTaskContract.MView? = null
    override fun onCreateView(mView: IListTaskContract.MView) {
        this.view = mView
    }

    override fun listTask(listingOption: ListOption, person: Person, pos: Int) {
        val seeOnlyMyTask = view?.isOnlyMyTaskList() ?: false
        val options: MOption<String, String, Any>? = when (person) {
            is Manager -> MOption("author", value = person.globalId)
            is TeamManager -> {
                if (!seeOnlyMyTask)
                    MOption("responsible", value = person.globalId)
                else
                    MOption("author", value = person.globalId)
            }
            is TeamMember -> MOption("responsible", value = person.globalId)
            else -> null
        }
        val optionAll: MOption<String, String, Any>? = when (listingOption) {
            ListOption.All -> {
                null
            }
            ListOption.Completed -> {
                MOption("state", value = StateTask.Closed)
            }
            ListOption.Incomplete -> {
                MOption("state", "!=", StateTask.Closed)
            }
        }


        val listOptions = arrayListOf<MOption<String, String, Any>>()
        optionAll?.let {
            listOptions.add(optionAll)
        }
        options?.let { listOptions.add(options) }
        val listTask =
            App.getManagerDB(context)?.getListTasks(where = listOptions) ?: arrayListOf()
        view?.showListTask(listTask, pos)
    }

    override fun onDestroy() {
        this.view = null
    }

}