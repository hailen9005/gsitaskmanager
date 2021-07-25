/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.R
import com.gsi.tm.enums.StateTask
import com.gsi.tm.helpers.App.createRecyclerView
import com.gsi.tm.interfaces.IEditTeamManagerContract
import com.gsi.tm.interfaces.IOnItemAdapter
import com.gsi.tm.models.Person
import com.gsi.tm.presenters.EditTeamMemberVPresenter
import kotlin.reflect.KClass

class EditTaskTeamManagerFragment : BaseFragment(), IEditTeamManagerContract.MView, IOnItemAdapter {
    //init ui
    private var rootView: View? = null
    private var recyclerView: RecyclerView? = null
    private var lyUserViewContainer: LinearLayout? = null
    private var imvSelectDestinatary: ImageView? = null

    //init properties
    val presenter: EditTeamMemberVPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = layoutInflater.inflate(R.layout.form_task_add, null)
        lyUserViewContainer = rootView?.findViewById(R.id.ly_person_container)
        imvSelectDestinatary = rootView?.findViewById(R.id.imv_profile_add_person)

        setActionUserPicker()
        setActionClearUserSelected()
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.onDestroy()
    }

    /**
     * clear selection user for tasks
     */
    private fun setActionClearUserSelected() {
        val imvCleanDestinatarys = rootView?.findViewById<ImageView>(R.id.btn_delete_all)
        imvCleanDestinatarys?.setOnClickListener {
            lyUserViewContainer?.removeAllViews()
        }
    }

    /**
     * show user picker
     */
    fun setActionUserPicker() {
        imvSelectDestinatary?.setOnClickListener {
            onClickSelection()
        }
    }

    private fun onClickSelection() {
        recyclerView?.let {
        } ?: kotlin.run {
            recyclerView = context?.createRecyclerView()
        }

        rootView?.let { rView ->
            super.showSelectPerson(rView, recyclerView!!, imvSelectDestinatary as View, this)
        }
    }

    override fun updateState(stateTask: StateTask) {

    }

    override fun goBack() {
        mNavigator?.goTo(TeamManagerFragment::class, null)
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {

    }

    val usersSelected = arrayListOf<Person>()
    override fun <T> onSelectectedItem(entity: T) {
        val person = entity as Person
        usersSelected.add(person)
        val fullName = person.fullName
        val profile = person.typeProfile

        popupWindow?.dismiss()
        val lyContainerUsers = rootView?.findViewById<LinearLayout>(R.id.ly_person_container)
        val item = layoutInflater.inflate(R.layout.item_person_min, null)
        item?.findViewById<TextView>(R.id.tv_full_name)?.text = fullName
        item?.findViewById<ImageButton>(R.id.imbtn_delete)?.setOnClickListener {
            lyContainerUsers?.removeView(item)
        }
        item.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            bottomMargin = resources.getDimensionPixelSize(R.dimen.d2dp)
        }

        lyContainerUsers?.addView(item)
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
