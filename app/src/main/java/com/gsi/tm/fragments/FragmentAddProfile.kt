package com.gsi.tm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gsi.tm.R
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.interfaces.ContractVP
import com.gsi.tm.interfaces.ContractViewAddProfile
import com.gsi.tm.presenters.FragmentAddProfileViewPresenter
import layout.FragmentChooseProfile
import kotlin.reflect.KClass

class FragmentAddProfile : BaseFragment(), ContractViewAddProfile.MView {
    private var rootView: View? = null
    var fmAddViewPresent: FragmentAddProfileViewPresenter? = null
    private lateinit var btnAdd: Button
    private var typeProfile: TypeProfile = TypeProfile.None

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.get("typeProfile")?.let {
            typeProfile = it as TypeProfile
        }

        //init views
        rootView = inflater.inflate(R.layout.add_profile, null)
        btnAdd = rootView!!.findViewById(R.id.btn_add_profile)

        //init presenter
        fmAddViewPresent = FragmentAddProfileViewPresenter()
        fmAddViewPresent?.onCreateView(this)

        //clickeable views
        btnAdd.setOnClickListener {
            fmAddViewPresent?.onClickAddProfile(TypeProfile.TeamMember)

            val targetFragment =
                when (typeProfile) {
                    TypeProfile.Manager -> FragmentGSIManager::class
                    TypeProfile.TeamManager -> FragmentGSITeamManager::class
                    TypeProfile.TeamMember -> FragmentGSITeamMember::class
                    else -> FragmentChooseProfile::class
                }
            goTo(targetFragment, TypeProfile.TeamMember)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        fmAddViewPresent?.onDestroy()
    }

    override fun goBack() {
        mnavigator?.goTo(FragmentChooseProfile::class, null)
    }


}