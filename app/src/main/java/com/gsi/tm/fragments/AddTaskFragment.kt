package com.gsi.tm.fragments

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.R
import com.gsi.tm.helpers.App
import com.gsi.tm.helpers.App.createRecyclerView
import com.gsi.tm.helpers.App.setAdaterToRecyclerView
import com.gsi.tm.helpers.App.showPopud
import com.gsi.tm.interfaces.IAddTaskViewPresentContract
import com.gsi.tm.interfaces.IOnItemAdapter
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.Person
import com.gsi.tm.presenters.AddTaskViewFragmentPresenter
import kotlin.reflect.KClass

class AddTaskFragment : BaseFragment(), IAddTaskViewPresentContract.MView, IOnItemAdapter {

    //ui init
    private var rootView: View? = null
    private var recyclerView: RecyclerView? = null

    //properties init
    private var personSelected: Person? = null
    var presenter: IAddTaskViewPresentContract.Presenter? = null
    var dateMillis = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = layoutInflater.inflate(R.layout.add_task, null)
        context?.let {
            presenter = AddTaskViewFragmentPresenter(it)
        }

        presenter?.onCreateView(this)
        setActionSelectPerson()
        setActionAssignTask()

        val btnCancel = rootView!!.findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            goBack()
        }
        return rootView
    }

    /**
     *  Assign task to user
     */
    private fun setActionAssignTask() {
        val btnAdd = rootView!!.findViewById<Button>(R.id.btn_assign_task)
        btnAdd.setOnClickListener {
            onClickAssign()
        }
    }

    private fun onClickAssign() {
        personSelected?.let {
            presenter?.onClickAddTask(getTaskDescriptionGSI())
        } ?: kotlin.run {
            context?.let {
                popupWindow = it.showPopud(
                    containerPopupView,
                    rootView!!,
                    getSizePopudHeigth400dp(it),
                    Point(0, 0)
                )
                setPopudMessages("Please select a person first from logo image")
            }
        }
    }

    fun setActionSelectPerson() {
        recyclerView = null
        recyclerView = context?.createRecyclerView()
        val imagePicker = rootView!!.findViewById<ImageView>(R.id.imv_profile_add_task)
        rootView?.let { rView ->
            showSelectPerson(rView, recyclerView!!, imagePicker, this)
        }
    }

    /**
     * build a task from UI
     *
     */
    private fun getTaskDescriptionGSI(): GSITaskDescription {
        //init
        val edtTitle = rootView?.findViewById<EditText>(R.id.edt_task_title)
        val edtDescription = rootView?.findViewById<EditText>(R.id.edt_task_description)
        val spin = rootView?.findViewById<Spinner>(R.id.spin_task_type)
        val spinProject = rootView?.findViewById<Spinner>(R.id.spin_task_project)
        val lyPickDate = rootView?.findViewById<LinearLayout>(R.id.ly_pick_date)
        lyPickDate?.setOnClickListener {
            presenter?.showPickerDate()
        }

        // values
        val tittle = edtTitle?.text.toString()
        val description = edtDescription?.text.toString()
        val type = (spin?.selectedItem as String)
        val responsible = personSelected?.globalId ?: "invalidId"
        val project = (spinProject?.selectedItem as String)
        val author = App.profileUser?.globalId ?: "invalidId"

        return GSITaskDescription(
            tittle = tittle,
            description = description,
            type = type,
            responsible = responsible,
            project = project,
            author = author,
            date = dateMillis
        )
    }


    /**
     * show PickerDate
     */
    private fun showPickerDate() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun goBack() {
        mNavigator?.goTo(ManagerFragment::class, null)
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {
        mNavigator?.goTo(fragmentClazz, null)
    }

    override fun onSelectedDate(dateMillis: Long) {
        this.dateMillis = dateMillis
        val dateStr = App.getDateFromMillis(dateMillis)
        rootView?.findViewById<TextView>(R.id.tv_date)?.text = dateStr
    }

    override fun onAddTask(result: Boolean, error: String?) {
        this.goBack()
    }

    override fun <T> onSelectectedItem(entity: T) {
        val person = entity as Person
        personSelected = person
        val fullName = person.fullName
        val profile = person.typeProfile

        rootView?.findViewById<TextView>(R.id.tv_profile_name)?.text = fullName
        rootView?.findViewById<TextView>(R.id.tv_profile_role)?.text = profile
        popupWindow?.dismiss()
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