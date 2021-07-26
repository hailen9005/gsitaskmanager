/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.ISeeDetailContract
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.MOption

class SeeDetailVPresenter(val context: Context) : ISeeDetailContract.Presenter {

    var mView: ISeeDetailContract.MView? = null

    override fun onCreateView(mView: ISeeDetailContract.MView) {
        this.mView = mView
    }

    override fun getTask(idTask: Long): GSITaskDescription? {
        val database = App.getManagerDB(context)
        val optionSelect = MOption<String, String, Any>("id", value = idTask)
        val gsiTask = database?.getListTasks(where = arrayListOf(optionSelect))?.firstOrNull()
        return gsiTask
    }

    override fun deleteTask(gsiTask: GSITaskDescription) {
        App.getManagerDB(context)?.delete(gsiTask)
        mView?.onDelete()
    }


    override fun onDestroy() {
        this.mView = null
    }

}
