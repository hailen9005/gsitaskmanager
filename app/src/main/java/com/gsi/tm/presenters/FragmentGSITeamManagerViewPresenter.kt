package com.gsi.tm.presenters

import com.gsi.tm.enums.ListOption
import com.gsi.tm.fragments.FragmentTeamMemberEditTask
import com.gsi.tm.interfaces.ContractTeamManagerGSI
import com.gsi.tm.interfaces.INavigate
import com.gsi.tm.models.GSITaskDescription

class FragmentTeamManagerViewPresenter : ContractTeamManagerGSI.Presenter {
    var view: ContractTeamManagerGSI.MView? = null
    var mnavigator: INavigate? = null


    override fun onCreateView(mView: ContractTeamManagerGSI.MView) {
        this.view = mView
    }

    override fun listTask(option: ListOption) {
        view?.listTask(option)
    }

    override fun onDestroy() {
        this.view = null
    }

    override fun onSelectectedTask(gsiTaskDescription: GSITaskDescription) {
        mnavigator?.goTo(FragmentTeamMemberEditTask::class, gsiTaskDescription.id)
    }


}