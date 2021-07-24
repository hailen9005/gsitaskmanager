package com.gsi.tm.presenters

import android.content.Context
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.*
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IMainActivViewPresentContract
import com.gsi.tm.interfaces.IComunication
import com.gsi.tm.interfaces.INavigate
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.MOption
import com.gsi.tm.models.Person
import layout.ChooseProfileFragment
import layout.AccountSelectFragment
import java.lang.Exception
import java.util.ArrayList
import kotlin.reflect.KClass

class MainActivityViewPresenter(val context: Context) : IMainActivViewPresentContract.Presenter,
    INavigate, IComunication {

    var supportFragmentManager: FragmentManager? = null
    var containerId: Int? = null
    var currentFragment: BaseFragment? = null
    var mainView: IMainActivViewPresentContract.Mview? = null

    override fun setSupportFragManager(supportFragment: FragmentManager, container: Int) {
        this.supportFragmentManager = supportFragment
        containerId = container
    }

    override fun onCreateView(mView: IMainActivViewPresentContract.Mview) {
        mainView = mView
    }

    override fun onDestroy() {
        mainView = null
    }


    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {
        fragmentClazz?.let {
            val transaction = this.supportFragmentManager?.beginTransaction()
            var enableBarStatus = false
            containerId?.let { id ->
                when (fragmentClazz) {
                    ChooseProfileFragment::class -> {
                        currentFragment = ChooseProfileFragment()
                    }
                    AddProfileFragment::class -> {
                        mainView?.enableBarStatus(false)
                        currentFragment = AddProfileFragment()
                        currentFragment?.arguments =
                            bundleOf(
                                Pair("typeProfile", param as TypeProfile)
                            )
                    }
                    ManagerFragment::class -> {
                        enableBarStatus = true
                        currentFragment = ManagerFragment()
                    }
                    TeamManagerFragment::class -> {
                        enableBarStatus = true
                        currentFragment = TeamManagerFragment()
                    }
                    TeamMemberFragment::class -> {
                        enableBarStatus = true
                        currentFragment = TeamMemberFragment()
                    }
                    AddTaskFragment::class -> {
                        enableBarStatus = true
                        currentFragment = AddTaskFragment()
                    }
                    EditTaskTeamMemberFragment::class -> {
                        enableBarStatus = true
                        currentFragment = EditTaskTeamMemberFragment()
                        currentFragment?.arguments =
                            bundleOf(
                                Pair("idTask", param as Long)
                            )
                    }
                    EditTaskManagerFragment::class -> {
                        enableBarStatus = true
                        currentFragment = EditTaskManagerFragment()
                        currentFragment?.arguments =
                            bundleOf(
                                Pair("idTask", param as Long)
                            )
                    }
                    EditTaskTeamManagerFragment::class -> {
                        enableBarStatus = true
                        currentFragment = EditTaskTeamManagerFragment()
                        currentFragment?.arguments =
                            bundleOf(
                                Pair("idTask", param as Long)
                            )
                    }
                    AccountSelectFragment::class -> {
                        currentFragment = AccountSelectFragment()
                        // (currentFragment as FragmentSelectAccount).setListPersons(param as ArrayList<Person>)
                    }

                    else -> {
                        throw(Exception("********Not declared Fragment into changer $fragmentClazz *********"))
                    }
                }

                currentFragment?.setNavigator(this)
                currentFragment?.setSender(this)
                mainView?.enableBarStatus(enableBarStatus)
                Log.e("currentFragment", " ${currentFragment!!::class.simpleName}")
                transaction?.replace(id, currentFragment!!, null)?.commit()
            }
        } ?: kotlin.run {
            Log.e("MainViewPresenter", "Exit from Application")
            mainView?.exit()
        }
    }

    override fun goBack() {
        currentFragment?.goBack()
    }

    override fun showSelectionUser(it: ArrayList<Person>) {
        goTo(AccountSelectFragment::class, it)
    }

    override fun loadHomeView() {

        val option = MOption<String, String, Any>("isAccountLocal", value = 1)
        App.getManagerDB(context)?.getListPersons(where = arrayListOf(option))?.let {
            if (it.isNotEmpty())
                showSelectionUser(it)
            else
                goTo(ChooseProfileFragment::class, null)
        } ?: kotlin.run {
            goTo(ChooseProfileFragment::class, null)
        }
    }


    //IComunication implement
    //
    override fun registerPerson(person: Person) {
        TODO("Not yet implemented")
    }

    override fun login(gsiTaskDescription: GSITaskDescription) {
        TODO("Not yet implemented")
    }

    override fun receiveTask(gsiTaskDescription: GSITaskDescription) {
        TODO("Not yet implemented")
    }

    override fun sendNewTask(
        gsiTaskDescription: GSITaskDescription,
        person: Person,
        fn: (success: Boolean, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }


}
