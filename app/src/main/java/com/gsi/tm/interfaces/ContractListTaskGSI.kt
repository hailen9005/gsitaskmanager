package com.gsi.tm.interfaces

import com.gsi.tm.enums.ListOption
import com.gsi.tm.models.GSITaskDescription

interface ContractListTaskGSI {
    interface MView : BaseView {
        fun listTask(listingOption: ListOption)
    }

    interface Presenter : BasePresenter<MView>, IGSISelectionTask {
        override fun onCreateView(mView: MView)
        fun listTask(option: ListOption)
    }

}
