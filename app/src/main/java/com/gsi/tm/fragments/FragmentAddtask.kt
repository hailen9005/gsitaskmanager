package com.gsi.tm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gsi.tm.R
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.ContractAddTaskVP
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.Person
import com.gsi.tm.presenters.FragmentAddTaskViewPresenter

class FragmentAddtask : BaseFragment(), ContractAddTaskVP.MView {

    private var rootView: View? = null

    //
    private val personSelected: Person? = null
    var presenter: ContractAddTaskVP.Presenter? = null
    var dateMillis = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = layoutInflater.inflate(R.layout.add_task, null)
        context?.let {
            presenter = FragmentAddTaskViewPresenter(it)
        }

        presenter?.onCreateView(this)
        val btnAdd = rootView!!.findViewById<Button>(R.id.btn_assign_task)
        btnAdd.setOnClickListener {
            presenter?.onClickAddTask(getTaskDescriptionGSI())
        }

        return rootView
    }

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
        val responsible = personSelected?.id ?: -1
        val project = (spinProject?.selectedItem as String)
        val author = presenter?.getAutor(context) ?: "unknow"

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

    private fun showPickerDate() {

    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun goBAck() {
        mnavigator?.setFragment(FragmentGSIManager::class, null)
    }

    override fun onSelectedDate(dateMillis: Long) {
        this.dateMillis = dateMillis
        val dateStr = App.getDateFromMillis(dateMillis)
        rootView?.findViewById<TextView>(R.id.tv_date)?.text = dateStr
    }


}