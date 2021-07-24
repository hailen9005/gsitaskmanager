package com.gsi.tm.interfaces

import com.gsi.tm.enums.TypeProfile

interface IViewAddProfileContract {

    interface MView : IBaseView {
        fun onAddProfile(profile: TypeProfile)
    }


    interface Presenter : IBasePresenter<MView> {
        fun onClickAddProfile(profile: TypeProfile, fullName: String, occupation: String)
    }


}
