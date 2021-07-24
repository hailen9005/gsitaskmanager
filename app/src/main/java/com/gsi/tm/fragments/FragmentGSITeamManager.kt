package com.gsi.tm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsi.tm.enums.ListOption
import com.gsi.tm.interfaces.ContractTeamManagerGSI
import com.gsi.tm.interfaces.IGSISelectionTask
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.presenters.FragmentTeamManagerViewPresenter
import kotlin.reflect.KClass

class FragmentGSITeamManager : FragmentGSIListTask(), IGSISelectionTask {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onSelectectedTask(gsiTaskDescription: GSITaskDescription) {
        // goTo(FragmentTeamMemberEditTask::class, gsiTaskDescription.id)
    }

}
