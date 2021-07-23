package com.gsi.tm.interfaces

import com.gsi.tm.enums.ListOption

interface ContractManagerGSI {
    interface MView {
        fun listTask(listingOption: ListOption)
    }

    interface Presenter : BasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun listTask(option: ListOption)
    }


}
