package com.gsi.tm.presenters

import android.content.Context
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IViewAddProfileContract
import com.gsi.tm.models.Manager
import com.gsi.tm.models.TeamManager
import com.gsi.tm.models.TeamMember

class AddProfileViewPresenterFragment(val context: Context) : IViewAddProfileContract.Presenter {
    var mView: IViewAddProfileContract.MView? = null

    override fun onCreateView(mView: IViewAddProfileContract.MView) {
        this.mView = mView
    }

    override fun onClickAddProfile(profile: TypeProfile, fullName: String, occupation: String) {
        val globalId = System.currentTimeMillis().toString()
        val person = when (profile) {
            TypeProfile.Manager -> Manager(
                fullName = fullName,
                occupation = occupation,
                globalId = globalId,
                isAccountLocal = true
            )
            TypeProfile.TeamManager -> TeamManager(
                fullName = fullName,
                occupation = occupation,
                globalId = globalId,
                isAccountLocal = true
            )
            TypeProfile.TeamMember -> TeamMember(
                fullName = fullName,
                occupation = occupation,
                globalId = globalId,
                isAccountLocal = true
            )
            else -> null
        }
        App.profileUser = person
        person?.let {
            App.getManagerDB(context)?.insert(person)
        }

        mView?.onAddProfile(profile)
    }


    override fun onDestroy() {
        this.mView = null
    }
}
