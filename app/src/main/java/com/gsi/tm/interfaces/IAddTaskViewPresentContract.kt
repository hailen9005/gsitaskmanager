package com.gsi.tm.interfaces

import com.gsi.tm.models.GSITaskDescription

interface IAddTaskViewPresentContract {
    interface MView : IBaseView {
        fun onSelectedDate(dateMillis: Long)
        fun onAddTask(result: Boolean, error: String?)
    }

    interface Presenter : IBasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun onClickAddTask(taskDescription: GSITaskDescription)
        fun showPickerDate()
    }

}
