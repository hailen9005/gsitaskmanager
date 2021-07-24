package com.gsi.tm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TabHost
import android.widget.TabHost.TabSpec
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.R
import com.gsi.tm.enums.ListOption
import com.gsi.tm.helpers.MTaskManagerAdapter
import com.gsi.tm.interfaces.ContractListTaskGSI
import com.gsi.tm.interfaces.IGSISelectionTask
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.presenters.FragmentManagerViewPresenter
import kotlin.reflect.KClass

open class FragmentGSIListTask : BaseFragment(), ContractListTaskGSI.MView, IGSISelectionTask {

    private lateinit var rootView: View
    var presenter: ContractListTaskGSI.Presenter? = null
    var recyclerView: RecyclerView? = null
    private var viewListContainer: View? = null
    private lateinit var btnAdd: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rootView = inflater.inflate(R.layout.manager_view_main, null)

        btnAdd = rootView.findViewById<ImageButton>(R.id.btn_add_task)
        val tabHost = rootView.findViewById<TabHost>(R.id.tabhost)
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

        viewListContainer = inflater.inflate(R.layout.listcontent_view, null)
        recyclerView = viewListContainer!!.findViewById(R.id.recycler_view)
        val lymanager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.layoutManager = lymanager
        recyclerView!!.setItemViewCacheSize(60)
        recyclerView!!.setHasFixedSize(true)

        setAdaterToRecyclerView()

        presenter = FragmentManagerViewPresenter()
        tabHost.tabWidget.getChildAt(0).setOnClickListener {
            presenter?.listTask(ListOption.All)
        }

        tabHost.tabWidget.getChildAt(1).setOnClickListener {
            presenter?.listTask(ListOption.Completed)
        }

        tabHost.tabWidget.getChildAt(2).setOnClickListener {
            presenter?.listTask(ListOption.Incomplete)
        }

        btnAdd.setOnClickListener {
            mnavigator?.goTo(FragmentAddtask::class, null)
        }

        return rootView
    }

    private fun setAdaterToRecyclerView() {
        context?.let { contx ->
            val mtaskAdapter = MTaskManagerAdapter(layoutInflater, contx)
            mtaskAdapter.listener = this
            recyclerView?.adapter = mtaskAdapter
        } ?: kotlin.run {
            Log.e("MTaskManagerAdapter", "context is null for MTaskManagerAdapter")
        }

        val lyContainer = rootView.findViewById<LinearLayout>(R.id.ly_container_1)
        lyContainer.addView(viewListContainer)
    }

    override fun listTask(listingOption: ListOption) {
        when (listingOption) {
            ListOption.All -> {
            }
            ListOption.Completed -> {
            }
            ListOption.Incomplete -> {
            }

        }
    }


    fun hideAddButton(hide: Boolean) {
        btnAdd.visibility = if (hide) View.INVISIBLE else View.VISIBLE
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.onCreateView(this)

    }

    override fun onSelectectedTask(gsiTaskDescription: GSITaskDescription) {

    }

}
