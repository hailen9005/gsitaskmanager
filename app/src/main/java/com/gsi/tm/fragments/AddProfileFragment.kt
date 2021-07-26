package com.gsi.tm.fragments

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.gsi.tm.R
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.manager.ManagerFragment
import com.gsi.tm.fragments.team_manager.TeamManagerFragment
import com.gsi.tm.fragments.team_member.TeamMemberFragment
import com.gsi.tm.helpers.App.requireLength
import com.gsi.tm.helpers.App.showPopud
import com.gsi.tm.interfaces.IComunication
import com.gsi.tm.interfaces.IViewAddProfileContract
import com.gsi.tm.presenters.AddProfileViewPresenterFragment
import layout.ChooseProfileFragment

class AddProfileFragment : BaseFragment(), IViewAddProfileContract.MView {
    private var rootView: View? = null
    var fmAddViewPresentFragment: AddProfileViewPresenterFragment? = null
    private lateinit var btnAdd: Button
    private lateinit var btnCancel: Button
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
        btnCancel = rootView!!.findViewById(R.id.btn_cancel_profile)
        val edtName: EditText = rootView!!.findViewById(R.id.edt_name)
        val spin: Spinner = rootView!!.findViewById(R.id.spin_occupation_type)


        //init presenter
        context?.let {
            fmAddViewPresentFragment = AddProfileViewPresenterFragment(it)
            fmAddViewPresentFragment?.onCreateView(this)
        }


        //clickeable views

        btnCancel.setOnClickListener {
            this.goBack()
        }
        btnAdd.setOnClickListener {
            if (/*edtName.isValidNameChars() and */edtName.requireLength(3)) {
                val name = edtName.text.toString()
                val occupation = (spin.selectedItem as String)
                fmAddViewPresentFragment?.onClickAddProfile(typeProfile, name, occupation)
            } else
                context?.let {
                    popupWindow = it.showPopud(
                        containerPopupView,
                        rootView!!,
                        getSizePopudHeigth400dp(it),
                        Point(0, 0)
                    )
                    setPopudMessages("A valid name is required")

                }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        fmAddViewPresentFragment?.onDestroy()
    }

    override fun goBack() {
        mNavigator?.goTo(ChooseProfileFragment::class, null)
    }

    override fun onAddProfile(profile: TypeProfile) {
        val targetFragment =
            when (typeProfile) {
                TypeProfile.Manager -> ManagerFragment::class
                TypeProfile.TeamManager -> TeamManagerFragment::class
                TypeProfile.TeamMember -> TeamMemberFragment::class
                else -> ChooseProfileFragment::class
            }

        goTo(targetFragment, typeProfile)

    }

    override fun getSender(): IComunication? {
        return mSender
    }


}