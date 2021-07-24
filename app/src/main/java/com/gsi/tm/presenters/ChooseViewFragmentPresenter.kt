package com.gsi.tm.presenters

import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.interfaces.IChoosePresenterContract

class ChooseViewFragmentPresenter : IChoosePresenterContract.Presenter {

    var mView: IChoosePresenterContract.MView? = null


    override fun onCreateView(mView: IChoosePresenterContract.MView) {
        this.mView = mView
    }

    override fun onClickSelectedTypeProfile(profile: TypeProfile) {

    }


    override fun onDestroy() {
        this.mView = null
    }

    // override fun setFragment(fragmentClazz: KClass<*>, param: Any?) {}

}