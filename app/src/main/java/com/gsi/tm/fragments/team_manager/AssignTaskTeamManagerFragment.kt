/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.fragments.team_manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.R
import com.gsi.tm.enums.StateTask
import com.gsi.tm.fragments.BaseFragment
import com.gsi.tm.helpers.App
import com.gsi.tm.helpers.App.createRecyclerView
import com.gsi.tm.interfaces.IEditTeamManagerContract
import com.gsi.tm.interfaces.IOnItemAdapter
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.Person
import com.gsi.tm.presenters.AssignTeamManagerVPresenter
import kotlin.reflect.KClass

class AssignTaskTeamManagerFragment : BaseFragment(), IEditTeamManagerContract.MView,
    IOnItemAdapter {
    //init ui
    private var rootView: View? = null
    private var recyclerView: RecyclerView? = null
    private var lyUserViewContainer: LinearLayout? = null
    private var imvSelectDestinatary: ImageView? = null
    private var edtTitle: EditText? = null
    private var edtDescription: EditText? = null
    private var spin: Spinner? = null
    private var spinProject: Spinner? = null
    private var lyPickDate: LinearLayout? = null
    private var btnAssign: Button? = null

    //init properties
    var presenter: AssignTeamManagerVPresenter? = null
    val usersListSelected = arrayListOf<Person>()
    var idTaskArguments = -1L
    var gsiTaskDescription: GSITaskDescription? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = layoutInflater.inflate(R.layout.add_task_team_manager, null)
        lyUserViewContainer = rootView?.findViewById(R.id.ly_person_container)
        imvSelectDestinatary = rootView?.findViewById(R.id.imv_profile_add_person)
        edtTitle = rootView?.findViewById(R.id.edt_task_title)
        edtDescription = rootView?.findViewById(R.id.edt_task_description)
        spin = rootView?.findViewById(R.id.spin_task_type)
        spinProject = rootView?.findViewById(R.id.spin_task_project)
        lyPickDate = rootView?.findViewById(R.id.ly_pick_date)
        btnAssign = rootView?.findViewById<Button>(R.id.btn_assign_task)


        arguments?.let {
            idTaskArguments = it.getLong("idTask")
        }
        setActionUserPicker()
        setActionDatePicker()
        setActionClearUserSelected()
        loadTaskFromDB(idTaskArguments)
        setActionAssignTask()

        return rootView
    }

    private fun setActionDatePicker() {
        lyPickDate?.setOnClickListener {
            // presenter?.showPickerDate()
        }
    }

    private fun setActionAssignTask() {
        btnAssign?.setOnClickListener {
            presenter?.assignTask(getListTaskDescriptionGSI(), usersListSelected)
        }
    }

    /**
     * set ui data
     */
    private fun loadTaskFromDB(idTaskArguments: Long) {
        context?.let { contx ->
            //configure ViewPresenter
            presenter = AssignTeamManagerVPresenter(contx)
            presenter?.onCreateView(this)
            presenter?.sender = mSender

            //update ui
            gsiTaskDescription = presenter?.getTask(idTaskArguments)
            edtTitle?.setText(gsiTaskDescription?.tittle ?: "")
            edtDescription?.setText(gsiTaskDescription?.tittle ?: "")
            val pos = resources.getStringArray(R.array.types).indexOf(gsiTaskDescription?.type)
            val posProjects =
                resources.getStringArray(R.array.projects).indexOf(gsiTaskDescription?.project)
            spin?.setSelection(pos)
            spinProject?.setSelection(posProjects)
        }

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
            usersListSelected.clear()
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


    /**
     * build a task from UI
     *
     */
    private fun getListTaskDescriptionGSI(): ArrayList<GSITaskDescription> {
        //for only test add for persons same GSI task
        val listAssignedTask = arrayListOf<GSITaskDescription>()
        usersListSelected.forEach { person ->
            // values
            val tittle = edtTitle?.text.toString()
            val description = edtDescription?.text.toString()
            val type = (spin?.selectedItem as String)
            val responsible = person.globalId ?: "invalidId"
            val project = (spinProject?.selectedItem as String)
            val author = App.profileUser?.globalId ?: "invalidId"
            val dateMillis = System.currentTimeMillis()

            val taskGsi = GSITaskDescription(
                tittle = tittle,
                description = description,
                type = type,
                responsible = responsible,
                project = project,
                author = author,
                date = dateMillis
            )
            listAssignedTask.add(taskGsi)
        }
        return listAssignedTask
    }


    override fun updateState(stateTask: StateTask) {

    }

    override fun onAddTask(result: Boolean, error: String?) {
        gsiTaskDescription?.stateTask = StateTask.Open
        presenter?.updateState(gsiTaskDescription!!)
        this.goBack()
    }

    override fun goBack() {
        activity?.runOnUiThread {
            mNavigator?.goTo(TeamManagerFragment::class, null)
        }
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {
        activity?.runOnUiThread { mNavigator?.goTo(fragmentClazz, null) }
    }

    override fun <T> onSelectectedItem(entity: T) {
        val person = entity as Person
        usersListSelected.add(person)
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
