package layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.gsi.tm.R
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.BaseFragment
import com.gsi.tm.fragments.AddProfileFragment
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IChoosePresenterContract
import com.gsi.tm.models.Person
import com.gsi.tm.presenters.ChooseViewFragmentPresenter
import kotlin.reflect.KClass

class ChooseProfileFragment : BaseFragment(), IChoosePresenterContract.MView {

    private var rootView: View? = null
    var fmChooseViewFragmentPresent: ChooseViewFragmentPresenter? = null
    private var lyManager: LinearLayout? = null
    private var lyTeamManager: LinearLayout? = null
    private var lyMemberTeam: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //init views
        rootView = inflater.inflate(R.layout.role_definition, null)
        lyManager = rootView?.findViewById(R.id.ly_manager_choose)
        lyTeamManager = rootView?.findViewById(R.id.ly_manager_team_choose)
        lyMemberTeam = rootView?.findViewById(R.id.ly_member_team_choose)

        //init presenter
        fmChooseViewFragmentPresent = ChooseViewFragmentPresenter()
        fmChooseViewFragmentPresent?.onCreateView(this)

        //clickeable views
        lyManager?.setOnClickListener {
            fmChooseViewFragmentPresent?.onClickSelectedTypeProfile(TypeProfile.Manager)
            goTo(AddProfileFragment::class, TypeProfile.Manager)
        }

        lyTeamManager?.setOnClickListener {
            fmChooseViewFragmentPresent?.onClickSelectedTypeProfile(
                TypeProfile.TeamManager
            )
            goTo(AddProfileFragment::class, TypeProfile.TeamManager)
        }

        lyMemberTeam?.setOnClickListener {
            fmChooseViewFragmentPresent?.onClickSelectedTypeProfile(
                TypeProfile.TeamMember
            )
            goTo(AddProfileFragment::class, TypeProfile.TeamMember)
        }

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        fmChooseViewFragmentPresent?.onDestroy()
    }

    override fun goBack() {
        var countUsers = 0
        context?.let {
            countUsers = App.getManagerDB(it)?.getCount<Person>() ?: 0
        }

        if (countUsers > 0)
            goTo(AccountSelectFragment::class, null)
        else
            super.goBack()
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {
        mNavigator?.goTo(fragmentClazz, param)
    }

}