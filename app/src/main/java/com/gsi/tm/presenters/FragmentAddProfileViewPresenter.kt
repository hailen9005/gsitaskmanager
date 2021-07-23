package com.gsi.tm.presenters

import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.FragmentAddProfile
import com.gsi.tm.interfaces.ContractVP
import com.gsi.tm.interfaces.ContractViewAddProfile
import com.gsi.tm.interfaces.INavigate

class FragmentAddProfileViewPresenter : ContractViewAddProfile.Presenter {
    var mView: ContractViewAddProfile.MView? = null

    override fun onCreateView(mView: ContractViewAddProfile.MView) {
        this.mView = mView
    }

    override fun onClickAddProfile(profile: TypeProfile) {

    }


    override fun onDestroy() {
        this.mView = null
    }
}
