package com.gsi.tm.presenters

import com.gsi.tm.enums.ListOption
import com.gsi.tm.interfaces.ContractListTaskGSI
import com.gsi.tm.models.GSITaskDescription

class FragmentManagerViewPresenter : ContractListTaskGSI.Presenter {
    var view: ContractListTaskGSI.MView? = null
    override fun onCreateView(mView: ContractListTaskGSI.MView) {
        this.view = mView
    }

    override fun listTask(option: ListOption) {
        view?.listTask(option)
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun onSelectectedTask(gsiTaskDescription: GSITaskDescription) {

    }


}