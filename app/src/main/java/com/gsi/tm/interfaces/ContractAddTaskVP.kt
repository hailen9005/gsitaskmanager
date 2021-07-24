package com.gsi.tm.interfaces

import android.content.Context
import com.gsi.tm.models.GSITaskDescription

interface ContractAddTaskVP {
    interface MView : BaseView {
        fun onSelectedDate(dateMillis: Long)
        fun onAddTask()
    }

    interface Presenter : BasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun onClickAddTask(taskDescription: GSITaskDescription)
        fun showPickerDate()
        fun getAutor(context: Context?): String
    }

}
