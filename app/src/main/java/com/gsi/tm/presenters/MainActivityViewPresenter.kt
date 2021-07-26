package com.gsi.tm.presenters

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.*
import com.gsi.tm.fragments.manager.EditTaskManagerFragment
import com.gsi.tm.fragments.manager.ManagerFragment
import com.gsi.tm.fragments.team_manager.AssignTaskTeamManagerFragment
import com.gsi.tm.fragments.team_manager.TeamManagerFragment
import com.gsi.tm.fragments.team_member.EditTaskTeamMemberFragment
import com.gsi.tm.fragments.team_member.TeamMemberFragment
import com.gsi.tm.helpers.App
import com.gsi.tm.helpers.WebConnect
import com.gsi.tm.interfaces.IMainActivViewPresentContract
import com.gsi.tm.interfaces.IComunication
import com.gsi.tm.interfaces.INavigate
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.MOption
import com.gsi.tm.models.Person
import layout.ChooseProfileFragment
import layout.AccountSelectFragment
import java.lang.Exception
import kotlin.collections.ArrayList
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
        subscribeUsersEvents()
    }

    override fun onDestroy() {
        mainView = null
    }


    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {
        fragmentClazz?.let {
            currentFragment?.let { currentFrg ->
                lastFragment = currentFrg::class
            }
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
                        currentFragment?.arguments = bundleOf(Pair("typeProfile", param))
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
                        currentFragment?.arguments = bundleOf(Pair("idTask", param))
                    }
                    EditTaskManagerFragment::class -> {
                        enableBarStatus = true
                        currentFragment = EditTaskManagerFragment()
                        currentFragment?.arguments = bundleOf(Pair("idTask", param))
                    }
                    AssignTaskTeamManagerFragment::class -> {
                        enableBarStatus = true
                        currentFragment = AssignTaskTeamManagerFragment()
                        currentFragment?.arguments = bundleOf(Pair("idTask", param))
                    }
                    SeeTaskDetailFragment::class -> {
                        enableBarStatus = true
                        currentFragment = SeeTaskDetailFragment()
                        currentFragment?.arguments = bundleOf(Pair("idTask", param))
                    }
                    AccountSelectFragment::class -> {
                        currentFragment = AccountSelectFragment()
                        // (currentFragment as FragmentSelectAccount).setListPersons(param as ArrayList<Person>)
                    }

                    else -> {
                        throw(Exception("********Not declared Fragment $fragmentClazz into ${this::class.simpleName}  *********"))
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

    private var _mlastFragment: KClass<*> = AccountSelectFragment::class
    override var lastFragment: KClass<*>?
        get() {
            return _mlastFragment
        }
        set(value) {
            _mlastFragment = value ?: AccountSelectFragment::class
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


    //
    //IComunication implement using firebase
    //
    override fun registerPerson(
        person: Person,
        function: (success: Boolean, error: String?) -> Unit
    ) {
        // FirebaseMessaging.getInstance().deleteToken()
        val sharedferences = context.getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE)
        val token = sharedferences.getString("token", null)
        token?.let {
            val listMessage = arrayListOf(Pair("token", token))
            listMessage.add(Pair("globalId", person.globalId))
            listMessage.add(Pair("fullName", person.fullName))
            listMessage.add(Pair("occupation", person.occupation))
            listMessage.add(Pair("typeProfile", person.typeProfile))
            val strJSON =
                WebConnect.contructMessage("token", "register", App.topicNewUser, listMessage)

            Thread(Runnable {
                WebConnect.connect(strJSON, function)
            }).start()
        }
    }

    override fun login(gsiTaskDescription: GSITaskDescription) {

    }


    override fun receiveTask(gsiTaskDescription: GSITaskDescription) {
        mainView?.notifyEvent(gsiTaskDescription)
    }

    override fun sendNewTask(
        gsiTaskDescription: GSITaskDescription,
        person: Person,
        function: (success: Boolean, error: String?) -> Unit
    ) {
        val strJSON =
            WebConnect.contructMessage("sampletask", "any content", App.topicTask, arrayListOf())

        Thread(Runnable {
            WebConnect.connect(strJSON, function)
        }).start()

    }

    override fun sendNewTasks(
        listTaskDescriptionGSI: ArrayList<GSITaskDescription>,
        users: ArrayList<Person>,
        function: (success: Boolean, error: String?) -> Unit
    ) {
        val strJSON =
            WebConnect.contructMessage("sampletask", "any content", App.topicTask, arrayListOf())

        Thread(Runnable {
            WebConnect.connect(strJSON, function)
        }).start()

    }

    override fun subscribeUsersEvents() {
        WebConnect.subscribe(App.topicNewUser) {
            //do something
        }
        WebConnect.subscribe(App.topicTask) {
            //do something
        }
    }


}
