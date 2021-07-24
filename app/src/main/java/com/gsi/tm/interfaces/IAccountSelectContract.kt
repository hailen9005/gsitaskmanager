/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.interfaces

import android.content.Context
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.models.Person

interface IAccountSelectContract {
    interface MView {

    }

    interface Presenter : IBasePresenter<IChoosePresenterContract.MView> {
        fun getListPersons(context: Context): ArrayList<Person>

    }
}
