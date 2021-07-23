package com.gsi.tm.presenters

import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.FragmentAddProfile
import com.gsi.tm.interfaces.ContractVP
import com.gsi.tm.interfaces.INavigate

class FragmentChooseViewPresenter : ContractVP.Presenter {

    var mView: ContractVP.MView? = null


    override fun onCreateView(mView: ContractVP.MView) {
        this.mView = mView
    }

    override fun onClickSelectedTypeProfile(profile: TypeProfile) {

    }


    override fun onDestroy() {
        this.mView = null
    }

    // override fun setFragment(fragmentClazz: KClass<*>, param: Any?) {}

}