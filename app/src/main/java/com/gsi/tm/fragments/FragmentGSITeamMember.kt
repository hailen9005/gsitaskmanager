package com.gsi.tm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsi.tm.interfaces.IGSISelectionTask
import com.gsi.tm.models.GSITaskDescription

class FragmentGSITeamMember : FragmentGSIListTask(), IGSISelectionTask {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideAddButton(true)
    }

    override fun onSelectectedTask(gsiTaskDescription: GSITaskDescription) {
        goTo(FragmentTeamMemberEditTask::class, gsiTaskDescription.id)
    }

}
