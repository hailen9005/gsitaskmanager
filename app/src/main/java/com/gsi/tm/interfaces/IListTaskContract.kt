package com.gsi.tm.interfaces

import com.gsi.tm.enums.ListOption
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.Person
import java.util.ArrayList

interface IListTaskContract {
    interface MView : IBaseView {
        fun showListTask(listTask: ArrayList<GSITaskDescription>, pos: Int)
        fun isOnlyMyTaskList(): Boolean
    }

    interface Presenter : IBasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun listTask(listingOption: ListOption, person: Person, pos: Int)
    }

}
