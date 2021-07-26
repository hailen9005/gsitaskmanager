/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.fragments.team_member

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gsi.tm.R
import com.gsi.tm.enums.StateTask
import com.gsi.tm.fragments.BaseFragment
import com.gsi.tm.helpers.App
import com.gsi.tm.helpers.App.getDrawableByState
import com.gsi.tm.helpers.App.showPopud
import com.gsi.tm.interfaces.IEditTeamMemberContract
import com.gsi.tm.presenters.EditTeamMemberVPresenter
import kotlin.reflect.KClass

class EditTaskTeamMemberFragment : BaseFragment(), IEditTeamMemberContract.MView {
    var rootView: View? = null
    var imvState: ImageView? = null
    var tvState: TextView? = null
    var presenter: IEditTeamMemberContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = layoutInflater.inflate(R.layout.team_member_update_task, null)
        //init
        val tvTitle = rootView?.findViewById<TextView>(R.id.tv_task_title)
        val tvDescription = rootView?.findViewById<TextView>(R.id.tv_task_description)
        val tvTaskType = rootView?.findViewById<TextView>(R.id.tv_task_type)
        val tvTaskProject = rootView?.findViewById<TextView>(R.id.tv_task_project)
        val tvDate = rootView?.findViewById<TextView>(R.id.tv_date)
        val btnInit = rootView?.findViewById<Button>(R.id.btn_init_task)
        val btnFinish = rootView?.findViewById<Button>(R.id.btn_finish_task)
        val btnChange = rootView?.findViewById<Button>(R.id.btn_change_task)
        imvState = rootView?.findViewById<ImageView>(R.id.imv_update_state)
        tvState = rootView?.findViewById<TextView>(R.id.tv_state)

        context?.let { cntx ->
            presenter = EditTeamMemberVPresenter(cntx)
            arguments?.get("idTask")?.let { idTask ->
                val gsiTask = presenter?.getTask(idTask as Long)
                gsiTask?.let { _gsiTask ->
                    val taskGSI = _gsiTask
                    tvTitle?.text = taskGSI.tittle
                    tvDescription?.text = taskGSI.description
                    tvTaskType?.text = taskGSI.type
                    tvTaskProject?.text = taskGSI.project
                    tvDate?.text = App.getDateFromMillis(taskGSI.date)
                    imvState?.setImageDrawable(cntx.getDrawableByState(gsiTask.stateTask))
                    tvState?.text = gsiTask.state

                    btnInit?.setOnClickListener {
                        taskGSI.stateTask = StateTask.InProgress
                        presenter?.updateState(taskGSI)
                    }
                    btnFinish?.setOnClickListener {
                        taskGSI.stateTask = StateTask.Closed
                        presenter?.updateState(taskGSI)
                    }
                    btnChange?.setOnClickListener {
                        //taskGSI.stateTask = StateTask.OffTime
                        //database.updateStateTask( taskGSI)

                        popupWindow = context?.showPopud(
                            containerPopupView,
                            rootView!!,
                            getSizePopudHeigth400dp(cntx),
                            Point(0, 0),
                            Gravity.CENTER
                        )
                        setPopudMessages("Need implement this ")

                    }
                } ?: kotlin.run { showTaskNotFound() }
            } ?: kotlin.run { showTaskNotFound() }
        }


        return rootView
    }

    private fun showTaskNotFound() {

        context?.let { cntx ->
            popupWindow = context?.showPopud(
                containerPopupView,
                rootView!!,
                getSizePopudHeigth400dp(cntx),
                Point(0, 0),
                Gravity.CENTER
            )
            setPopudMessages("Sorry can't find the task ")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.onCreateView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.onDestroy()
    }


    override fun updateState(stateTask: StateTask) {
        context?.let { cntx ->
            imvState?.setImageDrawable(cntx.getDrawableByState(stateTask))
            tvState?.text = stateTask.name
        }

        this.goBack()
    }

    override fun goBack() {
        mNavigator?.goTo(TeamMemberFragment::class, null)
    }

    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {
        mNavigator?.goTo(fragmentClazz, null)
    }

}
