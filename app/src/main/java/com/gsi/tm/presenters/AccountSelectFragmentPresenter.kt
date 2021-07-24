/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IAccountSelectContract
import com.gsi.tm.interfaces.IChoosePresenterContract
import com.gsi.tm.models.MOption
import com.gsi.tm.models.Person

class AccountSelectFragmentPresenter : IAccountSelectContract.Presenter {
    var mView: IChoosePresenterContract.MView? = null

    override fun getListPersons(context: Context): ArrayList<Person> {
        val options = MOption<String, String, Any>("isAccountLocal", value = 1)
        val listPerson =
            App.getManagerDB(context)?.getListPersons(where = arrayListOf(options)) ?: arrayListOf()
        return listPerson
    }

    override fun onDestroy() {
        this.mView = null
    }

    override fun onCreateView(mView: IChoosePresenterContract.MView) {
        this.mView = mView
    }

}
