package layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.gsi.tm.R
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.BaseFragment
import com.gsi.tm.fragments.FragmentAddProfile
import com.gsi.tm.interfaces.ContractVP
import com.gsi.tm.presenters.FragmentChooseViewPresenter
import kotlin.reflect.KClass

class FragmentChooseProfile : BaseFragment(), ContractVP.MView {

    private var rootView: View? = null
    var fmChooseViewPresent: FragmentChooseViewPresenter? = null
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
        fmChooseViewPresent = FragmentChooseViewPresenter()
        fmChooseViewPresent?.onCreateView(this)

        //clickeable views
        lyManager?.setOnClickListener {
            fmChooseViewPresent?.onClickSelectedTypeProfile(TypeProfile.Manager)
            navigateTo(FragmentAddProfile::class, TypeProfile.Manager)

        }

        lyTeamManager?.setOnClickListener {
            fmChooseViewPresent?.onClickSelectedTypeProfile(
                TypeProfile.TeamManager
            )
            navigateTo(FragmentAddProfile::class, TypeProfile.TeamManager)
        }

        lyMemberTeam?.setOnClickListener {
            fmChooseViewPresent?.onClickSelectedTypeProfile(
                TypeProfile.TeamMember
            )
            navigateTo(FragmentAddProfile::class, TypeProfile.TeamMember)
        }

        return rootView
    }

    private fun navigateTo(kClass: KClass<*>, toProfile: TypeProfile) {
        mnavigator?.setFragment(kClass, toProfile)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        fmChooseViewPresent?.onDestroy()
    }

}