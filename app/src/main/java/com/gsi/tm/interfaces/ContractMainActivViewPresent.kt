package com.gsi.tm.interfaces

import androidx.fragment.app.FragmentManager
import com.gsi.tm.interfaces.BasePresenter
import com.gsi.tm.interfaces.BaseView
import com.gsi.tm.models.GSITaskDescription


interface ContractMainActivViewPresent {

    interface Mview : BaseView {
        fun exit()
        fun enableBarStatus(visible: Boolean)
        fun onSendMessage()
        fun onReceiveMessage(message: GSITaskDescription)
    }

    interface Presenter : BasePresenter<Mview> {
        fun setSupportFragManager(supportFragment: FragmentManager, container: Int)
        override fun onCreateView(mView: Mview)
    }
}
