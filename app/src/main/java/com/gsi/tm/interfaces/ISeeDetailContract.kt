/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.interfaces

import com.gsi.tm.enums.StateTask
import com.gsi.tm.models.GSITaskDescription

class ISeeDetailContract {
    interface MView : IBaseView {
        fun onDelete()
    }

    interface Presenter : IBasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun getTask(idTask: Long): GSITaskDescription?
        fun deleteTask(gsiTask: GSITaskDescription)
    }
}
