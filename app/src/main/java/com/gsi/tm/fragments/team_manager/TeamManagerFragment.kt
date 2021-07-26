/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.fragments.team_manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsi.tm.fragments.ListTaskFragment
import com.gsi.tm.fragments.SeeTaskDetailFragment
import com.gsi.tm.helpers.App
import com.gsi.tm.interfaces.IOnItemAdapter
import com.gsi.tm.models.GSITaskDescription

class TeamManagerFragment : ListTaskFragment(), IOnItemAdapter {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun <T> onSelectectedItem(entity: T) {
        val gsiTaskDescription = entity as GSITaskDescription
        if (gsiTaskDescription.author == App.profileUser?.globalId)
            goTo(SeeTaskDetailFragment::class, gsiTaskDescription.id)
        else
            goTo(AssignTaskTeamManagerFragment::class, gsiTaskDescription.id)
    }

}
