package com.gsi.tm.interfaces

import com.gsi.tm.enums.TypeProfile

interface ContractViewAddProfile {

    interface MView : BaseView {

    }


    interface Presenter : BasePresenter<MView> {
        fun onClickAddProfile(profile: TypeProfile)
    }


}
