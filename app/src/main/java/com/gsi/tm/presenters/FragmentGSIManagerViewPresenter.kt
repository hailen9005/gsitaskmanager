package com.gsi.tm.presenters

import com.gsi.tm.enums.ListOption
import com.gsi.tm.interfaces.ContractManagerGSI

class FragmentManagerViewPresenter : ContractManagerGSI.Presenter {
    var view: ContractManagerGSI.MView? = null
    override fun onCreateView(mView: ContractManagerGSI.MView) {
        this.view = mView
    }

    override fun listTask(option: ListOption) {
        view?.listTask(option)
    }

    override fun onDestroy() {
        this.view = null
    }


}