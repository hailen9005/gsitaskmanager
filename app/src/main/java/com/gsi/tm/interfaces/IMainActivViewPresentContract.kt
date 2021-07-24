package com.gsi.tm.interfaces

import androidx.fragment.app.FragmentManager
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.Person
import java.util.ArrayList


interface IMainActivViewPresentContract {

    interface Mview : IBaseView {
        fun exit()
        fun enableBarStatus(visible: Boolean)
        fun onSendMessage()
        fun onReceiveMessage(message: GSITaskDescription)
        fun onSelectedUser(person: Person)
    }

    interface Presenter : IBasePresenter<Mview> {
        fun setSupportFragManager(supportFragment: FragmentManager, container: Int)
        override fun onCreateView(mView: Mview)
        fun showSelectionUser(it: ArrayList<Person>)
        fun loadHomeView()
    }
}
