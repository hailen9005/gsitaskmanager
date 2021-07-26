/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.interfaces

import com.gsi.tm.enums.StateTask
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.Person
import java.util.ArrayList

interface IEditTeamManagerContract {

    interface MView : IBaseView {
        fun updateState(stateTask: StateTask)
        fun onAddTask(result: Boolean, error: String?)
    }

    interface Presenter : IBasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun updateState(gsiTask: GSITaskDescription)
        fun getTask(idTask: Long): GSITaskDescription?
        fun assignTask(
            listTaskDescriptionGSI: ArrayList<GSITaskDescription>,
            assignmentList: ArrayList<Person>
        )
    }
}
