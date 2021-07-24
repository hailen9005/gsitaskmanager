package com.gsi.tm.interfaces

import com.gsi.tm.enums.ListOption

interface ContractTeamManagerGSI {
    interface MView : BaseView {
        fun listTask(listingOption: ListOption)
    }

    interface Presenter : BasePresenter<MView>, IGSISelectionTask {
        override fun onCreateView(mView: MView)
        fun listTask(option: ListOption)
    }


}
