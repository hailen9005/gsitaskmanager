package com.gsi.tm.interfaces

import android.content.Context
import com.gsi.tm.models.GSITaskDescription

interface ContractAddTaskVP {
    interface MView : BaseView {
        fun goBAck()
        fun onSelectedDate(dateMillis: Long)
    }

    interface Presenter : BasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun onClickAddTask(taskDescription: GSITaskDescription)
        fun showPickerDate()
        fun getAutor(context: Context?): String
    }

}
