package com.gsi.tm.interfaces

import com.gsi.tm.enums.TypeProfile

interface IChoosePresenterContract {

    interface MView : IBaseView {

    }


    interface Presenter : IBasePresenter<MView> {
        fun onClickSelectedTypeProfile(profile: TypeProfile)
    }


}
