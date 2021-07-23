package com.gsi.tm.interfaces

import com.gsi.tm.enums.TypeProfile
import kotlin.reflect.KClass

interface ContractVP {

    interface MView : BaseView {

    }


    interface Presenter : BasePresenter<MView> {
        fun onClickSelectedTypeProfile(profile: TypeProfile)
    }


}
