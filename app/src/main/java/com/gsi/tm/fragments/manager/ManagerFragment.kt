/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.fragments.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gsi.tm.fragments.ListTaskFragment
import com.gsi.tm.fragments.SeeTaskDetailFragment
import com.gsi.tm.interfaces.IOnItemAdapter
import com.gsi.tm.models.GSITaskDescription

class ManagerFragment : ListTaskFragment(), IOnItemAdapter {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun <T> onSelectectedItem(entity: T) {
        val gsiTaskDescription = entity as GSITaskDescription
        //  goTo(EditTaskManagerFragment::class, gsiTaskDescription.id)
        goTo(SeeTaskDetailFragment::class, gsiTaskDescription.id)
    }


}
