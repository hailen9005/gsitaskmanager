package com.gsi.tm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.TabHost.TabSpec
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.R
import com.gsi.tm.enums.ListOption
import com.gsi.tm.helpers.App
import com.gsi.tm.helpers.App.createRecyclerView
import com.gsi.tm.helpers.App.getDrawableByState
import com.gsi.tm.helpers.App.setAdaterToRecyclerView
import com.gsi.tm.interfaces.IListTaskContract
import com.gsi.tm.interfaces.IOnItemAdapter
import com.gsi.tm.models.*
import com.gsi.tm.presenters.ListTaskFragmentViewPresenter
import layout.AccountSelectFragment

open class ListTaskFragment : BaseFragment(), IListTaskContract.MView, IOnItemAdapter {

    //ui
    var rootView: View? = null
    var btnAssignedTasks: ImageButton? = null
    var btnMyTasks: ImageButton? = null
    lateinit var tabHost: TabHost
    private lateinit var btnAdd: ImageButton

    // properties
    var presenter: IListTaskContract.Presenter? = null
    var recyclerView: RecyclerView? = null
    var seeOnlyMyTask = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rootView = inflater.inflate(R.layout.main_panel_list_task, null)
        btnAdd = rootView!!.findViewById(R.id.btn_add_task)!!
        btnMyTasks = rootView!!.findViewById(R.id.btn_see_personal_task)!!
        btnAssignedTasks = rootView!!.findViewById(R.id.btn_see_group_task)!!
        tabHost = rootView!!.findViewById(R.id.tabhost)
        tabHost.setup()

        var spec: TabSpec = tabHost.newTabSpec("tag1")
        spec.setContent(R.id.tab1)
        spec.setIndicator(getString(R.string.listAll))
        tabHost.addTab(spec)

        spec = tabHost.newTabSpec("tag2")
        spec.setContent(R.id.tab2)
        spec.setIndicator(getString(R.string.completed))
        tabHost.addTab(spec)

        spec = tabHost.newTabSpec("tag3")
        spec.setContent(R.id.tab2)
        spec.setIndicator(getString(R.string.noCompleted))

        tabHost.addTab(spec)

        val l = tabHost.tabWidget.getChildAt(0) as LinearLayout
        val l2 = tabHost.tabWidget.getChildAt(1) as LinearLayout
        val l3 = tabHost.tabWidget.getChildAt(2) as LinearLayout
        val color = resources.getColor(R.color.colorPrimary)

        (l.getChildAt(1) as TextView).setTextColor(color)
        (l2.getChildAt(1) as TextView).setTextColor(color)
        (l3.getChildAt(1) as TextView).setTextColor(color)

        tabHost.tabWidget.getChildAt(0).setOnClickListener {
            onTabSelected(ListOption.All, 0)
        }

        tabHost.tabWidget.getChildAt(1).setOnClickListener {
            onTabSelected(ListOption.Completed, 1)
        }

        tabHost.tabWidget.getChildAt(2).setOnClickListener {
            onTabSelected(ListOption.Incomplete, 2)
        }

        btnAdd.setOnClickListener {
            mNavigator?.goTo(AddTaskFragment::class, null)
        }

        btnMyTasks?.setOnClickListener {
            seeOnlyMyTask = false
            onTabSelected(ListOption.Incomplete, tabHost.currentTab)
        }

        btnAssignedTasks?.setOnClickListener {
            seeOnlyMyTask = true
            onTabSelected(ListOption.Incomplete, tabHost.currentTab)
        }

        App.profileUser?.let { person ->
            if (person !is TeamManager)
                (btnAssignedTasks?.parent as? View)?.visibility = View.GONE
        }

        return rootView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            presenter = ListTaskFragmentViewPresenter(it)
            presenter?.onCreateView(this)
            recyclerView = it.createRecyclerView()
            val lyContainer = rootView?.findViewById<LinearLayout>(R.id.ly_container_1)
            lyContainer?.addView(recyclerView)
            App.profileUser?.let { person ->
                presenter?.listTask(ListOption.All, person, 0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.onDestroy()

    }

    fun onTabSelected(listingOption: ListOption, pos: Int) {
        App.profileUser?.let { person ->
            presenter?.listTask(listingOption, person, pos)
        }
        tabHost.currentTab = pos
    }

    override fun showListTask(listTask: ArrayList<GSITaskDescription>, pos: Int) {
        context?.let {
            activity?.runOnUiThread {
                recyclerView?.setAdaterToRecyclerView(listTask, this, R.layout.item_task, it)
                recyclerView?.adapter?.notifyDataSetChanged()
                val resId = when (pos) {
                    0 -> R.id.ly_container_1
                    1 -> R.id.ly_container_2
                    else -> R.id.ly_container_3
                }
                val lyContainer = rootView?.findViewById<LinearLayout>(resId)
                (recyclerView?.parent as LinearLayout).removeAllViews()
                lyContainer?.addView(recyclerView)
            }
        }
    }


    fun hideAddButton(hide: Boolean) {
        btnAdd.visibility = if (hide) View.INVISIBLE else View.VISIBLE
    }


    override fun <T> onSelectectedItem(entity: T) {

    }

    override fun detachedFromWindow(itemView: View) {
        itemView.findViewById<ImageView>(R.id.imv_profile_item_task).setImageBitmap(null)
    }

    override fun <T> constructView(entity: T, itemView: View) {
        val gsiTaskDescription = entity as GSITaskDescription
        val dateStr = App.getDateFromMillis(gsiTaskDescription.date)
        val state = gsiTaskDescription.stateTask
        val drawableByState = context?.getDrawableByState(state)
        // imvProfile = this.itemView.findViewById(R.id.imv_profile_item_task)
        itemView.findViewById<TextView>(R.id.tv_title).text = gsiTaskDescription.tittle
        itemView.findViewById<TextView>(R.id.tv_date_item_task).text = dateStr
        itemView.findViewById<TextView>(R.id.tv_state).text = state.name
        itemView.findViewById<ImageView>(R.id.imv_state).setImageDrawable(drawableByState)
        itemView.setOnClickListener {
            onSelectectedItem(gsiTaskDescription)
        }
    }

    override fun goBack() {
        var countUsers = 0
        context?.let {
            countUsers = App.getManagerDB(it)?.getCount<Person>() ?: 0
        }

        if (countUsers > 0)
            goTo(AccountSelectFragment::class, null)
        else
            super.goBack()
    }

    override fun isOnlyMyTaskList() = seeOnlyMyTask

}
