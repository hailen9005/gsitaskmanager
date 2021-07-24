package com.gsi.tm.presenters

import com.gsi.tm.enums.ListOption
import com.gsi.tm.interfaces.ITeamManagerContract
import com.gsi.tm.interfaces.INavigate

class FragmentTeamManagerViewPresenter : ITeamManagerContract.Presenter {
    var view: ITeamManagerContract.MView? = null
    var mnavigator: INavigate? = null


    override fun onCreateView(mView: ITeamManagerContract.MView) {
        this.view = mView
    }

    override fun listTask(option: ListOption) {
        view?.listTask(option)
    }

    override fun onDestroy() {
        this.view = null
    }


}