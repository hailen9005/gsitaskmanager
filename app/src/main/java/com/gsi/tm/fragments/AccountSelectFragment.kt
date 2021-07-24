package layout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gsi.tm.R
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.*
import com.gsi.tm.helpers.App
import com.gsi.tm.helpers.App.createRecyclerView
import com.gsi.tm.helpers.App.setAdaterToRecyclerView
import com.gsi.tm.interfaces.IAccountSelectContract
import com.gsi.tm.interfaces.IOnItemAdapter
import com.gsi.tm.models.*
import com.gsi.tm.presenters.AccountSelectFragmentPresenter
import java.util.ArrayList
import kotlin.reflect.KClass

class AccountSelectFragment : BaseFragment(), IAccountSelectContract.MView, IOnItemAdapter {

    private var rootView: View? = null
    private var btnAddProfile: ImageButton? = null
    var presenter: IAccountSelectContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //init views
        rootView = inflater.inflate(R.layout.select_account, null)
        btnAddProfile = rootView?.findViewById(R.id.btn_add_profile)
        //clickeable views
        btnAddProfile?.setOnClickListener { goTo(ChooseProfileFragment::class, null) }

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AccountSelectFragmentPresenter()
        context?.let {
            val recyclerView = it.createRecyclerView()
            val lyContainer = rootView!!.findViewById<LinearLayout>(R.id.ly_container_1)
            lyContainer.addView(recyclerView)
            val listPerson = presenter?.getListPersons(it) ?: arrayListOf()
            recyclerView.setAdaterToRecyclerView(listPerson, this, R.layout.item_person, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun goBack() {
        super.goBack()
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {
        mNavigator?.goTo(fragmentClazz, param)
    }

    override fun <T> onSelectectedItem(entity: T) {
        val person = entity as Person
        val targetFragment: KClass<*>
        when (person.typeProfile) {
            TypeProfile.Manager.name -> {
                App.profileUser = Manager(
                    person.fullName,
                    person.occupation,
                    person.globalId,
                    person.isAccountLocal
                )
                targetFragment = ManagerFragment::class
            }
            TypeProfile.TeamManager.name -> {
                App.profileUser = TeamManager(
                    person.fullName,
                    person.occupation,
                    person.globalId,
                    person.isAccountLocal
                )
                targetFragment = TeamManagerFragment::class
            }
            TypeProfile.TeamMember.name -> {
                App.profileUser = TeamMember(
                    person.fullName,
                    person.occupation,
                    person.globalId,
                    person.isAccountLocal
                )
                targetFragment = TeamMemberFragment::class
            }
            else -> targetFragment = AccountSelectFragment::class
        }
        if (targetFragment != AccountSelectFragment::class) {
            goTo(targetFragment, entity.id)
        } else
            Log.e("Error", "onSelectectedItem")

    }


    override fun detachedFromWindow(itemView: View) {

    }

    override fun <T> constructView(entity: T, itemView: View) {
        val person = entity as Person
        val fullName = person.fullName
        val profile = person.typeProfile

        val imvProfile: ImageView = itemView.findViewById(R.id.imv_profile_item)
        itemView.findViewById<TextView>(R.id.tv_profile_name).text = fullName
        itemView.findViewById<TextView>(R.id.tv_type_profile).text = profile
        itemView.setOnClickListener {
            onSelectectedItem(person)
        }
    }

}
