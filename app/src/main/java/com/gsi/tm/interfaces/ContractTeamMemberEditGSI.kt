/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.interfaces

import android.view.View
import com.gsi.tm.enums.StateTask
import com.gsi.tm.models.GSITaskDescription

interface ContractTeamMemberEditGSI {

    interface MView : BaseView {
        fun updateState(stateTask: StateTask)
    }

    interface Presenter : BasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun updateState(gsiTask: GSITaskDescription)
        fun getTask(idTask: Long): GSITaskDescription?
    }
}
