package com.gsi.tm.presenters

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

    override fun onDestroy() {

    }

    override fun onCreateView(mView: ContractMainActivViewPresent.Mview) {
        mainView = mView
    }

    override fun setFragment(fragmentClazz: KClass<*>, param: Any?) {
        val transaction = this.supportFragmentManager?.beginTransaction()
        containerId?.let { id ->
            when (fragmentClazz) {

                FragmentChooseProfile::class -> {
                    mainView?.enableBarStatus(false)
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
                    currentFragment = FragmentGSIManager()
                    mainView?.enableBarStatus(true)
                }
                FragmentGSITeamManager::class -> {
                    currentFragment = FragmentGSITeamManager()
                    mainView?.enableBarStatus(true)
                }
                FragmentGSITeamMember::class -> {
                    currentFragment = FragmentGSITeamMember()
                    mainView?.enableBarStatus(true)
                }


                FragmentAddtask::class -> {
                    currentFragment = FragmentAddtask()
                    mainView?.enableBarStatus(true)
                }

                else -> {
                    throw(Exception("********Not declared Fragment into changer $fragmentClazz *********"))
                }
            }

            currentFragment?.setNavigator(this)
            transaction?.replace(id, currentFragment!!, null)?.commit()
        }
    }


}
