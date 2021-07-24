package com.gsi.tm.presenters

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.gsi.tm.enums.TypeProfile
import com.gsi.tm.fragments.*
import com.gsi.tm.interfaces.ContractMainActivViewPresent
import com.gsi.tm.interfaces.INavigate
import layout.FragmentChooseProfile
import java.lang.Exception
import kotlin.reflect.KClass

class MainActivityViewPresenter : ContractMainActivViewPresent.Presenter, INavigate {

    var supportFragmentManager: FragmentManager? = null
    var containerId: Int? = null
    var currentFragment: BaseFragment? = null
    var mainView: ContractMainActivViewPresent.Mview? = null

    override fun setSupportFragManager(supportFragment: FragmentManager, container: Int) {
        this.supportFragmentManager = supportFragment
        containerId = container
    }

    override fun onCreateView(mView: ContractMainActivViewPresent.Mview) {
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
                    FragmentChooseProfile::class -> {
                        currentFragment = FragmentChooseProfile()
                    }
                    FragmentAddProfile::class -> {
                        mainView?.enableBarStatus(false)
                        currentFragment = FragmentAddProfile()
                        currentFragment?.arguments =
                            bundleOf(
                                Pair("typeProfile", param as TypeProfile)
                            )
                    }
                    FragmentGSIManager::class -> {
                        enableBarStatus = true
                        currentFragment = FragmentGSIManager()
                    }
                    FragmentGSITeamManager::class -> {
                        enableBarStatus = true
                        currentFragment = FragmentGSITeamManager()
                    }
                    FragmentGSITeamMember::class -> {
                        enableBarStatus = true
                        currentFragment = FragmentGSITeamMember()
                    }
                    FragmentAddtask::class -> {
                        enableBarStatus = true
                        currentFragment = FragmentAddtask()
                    }
                    FragmentTeamMemberEditTask::class -> {
                        enableBarStatus = true
                        currentFragment = FragmentTeamMemberEditTask()
                        currentFragment?.arguments =
                            bundleOf(
                                Pair("idTask", param as Long)
                            )
                    }
                    FragmentManagerEditTask::class -> {
                        enableBarStatus = true
                        currentFragment = FragmentManagerEditTask()
                        currentFragment?.arguments =
                            bundleOf(
                                Pair("idTask", param as Long)
                            )
                    }

                    else -> {
                        throw(Exception("********Not declared Fragment into changer $fragmentClazz *********"))
                    }
                }

                currentFragment?.setNavigator(this)
                transaction?.replace(id, currentFragment!!, null)?.commit()
                mainView?.enableBarStatus(enableBarStatus)
            }
        } ?: kotlin.run {
            Log.e("MainViewPresenter", "Exit from Application")
            mainView?.exit()
        }
    }

    override fun goBack() {
        currentFragment?.goBack()
    }


}
