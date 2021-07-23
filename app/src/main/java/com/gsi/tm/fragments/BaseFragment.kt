package com.gsi.tm.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gsi.tm.interfaces.INavigate

open class BaseFragment : Fragment() {

    var mnavigator: INavigate? = null

    fun setNavigator(navigator: INavigate?) {
        this.mnavigator = navigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }


}
