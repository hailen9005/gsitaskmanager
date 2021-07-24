package com.gsi.tm.interfaces

import com.gsi.tm.enums.ListOption

interface ITeamManagerContract {
    interface MView : IBaseView {
        fun listTask(listingOption: ListOption)
    }

    interface Presenter : IBasePresenter<MView> {
        override fun onCreateView(mView: MView)
        fun listTask(option: ListOption)
    }


}
