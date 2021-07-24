/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.interfaces

import com.gsi.tm.enums.StateTask
import com.gsi.tm.models.GSITaskDescription

interface IEditTeamManagerContract {

    interface MView : IBaseView {
        fun updateState(stateTask: StateTask)
    }

    interface Presenter : IBasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun updateState(gsiTask: GSITaskDescription)
        fun getTask(idTask: Long): GSITaskDescription?
    }
}
