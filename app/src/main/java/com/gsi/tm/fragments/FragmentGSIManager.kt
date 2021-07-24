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

class FragmentGSIManager : FragmentGSIListTask(), IGSISelectionTask {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
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


    override fun onSelectectedTask(gsiTaskDescription: GSITaskDescription) {
        goTo(FragmentManagerEditTask::class, gsiTaskDescription.id)
    }


}
