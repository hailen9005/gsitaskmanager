package com.gsi.tm.fragments

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.R
import com.gsi.tm.helpers.App
import com.gsi.tm.helpers.App.createRecyclerView
import com.gsi.tm.helpers.App.setAdaterToRecyclerView
import com.gsi.tm.helpers.App.showPopud
import com.gsi.tm.interfaces.IComunication
import com.gsi.tm.interfaces.INavigate
import com.gsi.tm.interfaces.IOnItemAdapter
import kotlinx.android.synthetic.main.role_member_team.*
import kotlin.reflect.KClass

open class BaseFragment : Fragment(), INavigate {

    var mSender: IComunication? = null
    var mNavigator: INavigate? = null

    /**
     * navigation between framents
     */
    fun setNavigator(navigator: INavigate?) {
        this.mNavigator = navigator
    }

    /**
     * IComunication for send task desciption and other data to internet
     */
    fun setSender(sender: IComunication) {
        this.mSender = sender
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
        popupWindow?.dismiss()
    }

    /***
     *  config popud
     */
    var popupWindow: PopupWindow? = null
    var lyList: LinearLayout? = null
    var ctvView: View? = null
    val containerPopupView: View
        get() {
            ctvView?.let {

            } ?: run {
                ctvView = layoutInflater.inflate(R.layout.popud_message, null)
                ctvView?.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )

                ctvView?.findViewById<Button>(R.id.btn_ok)?.setOnClickListener {
                    popupWindow?.dismiss()
                }

            }
            return ctvView!!
        }

    fun setPopudMessages(header: String, body: String? = null) {
        containerPopupView.findViewById<TextView>(R.id.tv_popud_header).text = header
        containerPopupView.findViewById<TextView>(R.id.tv_popud_body).text = body
    }

    fun getSizePopudHeigth400dp(cntx: Context): Point {
        val sizeScreen = App.getScreenSize(cntx)
        val w = (sizeScreen.x - (sizeScreen.x / 3))
        val h = resources.getDimension(R.dimen.d400dp).toInt()
        val sizePopud = Point(w, h)
        return sizePopud
    }


    /**
     * select a User from floating list
     */
    fun showSelectPerson(
        rootView: View,
        recyclerView: RecyclerView,
        viewPicker: View,
        listener: IOnItemAdapter
    ) {
        this.recyclerView = recyclerView
        viewPicker.setOnClickListener {
            onClickPicker(rootView, listener)
        }

    }

    private var recyclerView: RecyclerView? = null


    private fun onClickPicker(
        rootView: View,
        listener: IOnItemAdapter
    ) {
        context?.let {
            (recyclerView?.parent as? LinearLayout)?.removeAllViews()
            popupWindow?.dismiss()
            val lyContainer: ConstraintLayout =
                layoutInflater.inflate(R.layout.ly_container_recycler, null) as ConstraintLayout
            val lyRecyclerContainer =
                lyContainer.findViewById<LinearLayout>(R.id.ly_container_recycler)
            lyRecyclerContainer.addView(recyclerView)
            val size = App.getScreenSize(it)
            val widthHeight = Point(size.x - (size.x / 3), size.y - (size.y / 3))
            popupWindow = context?.showPopud(lyContainer, rootView, widthHeight, Point(0, 0))
            val listTask = App.getManagerDB(it)?.getListPersons() ?: arrayListOf()
            recyclerView?.setAdaterToRecyclerView(listTask, listener, R.layout.item_person, it)
            val btnDismissPopud: Button = lyContainer.findViewById(R.id.btn_cancel)
            btnDismissPopud.setOnClickListener {
                popupWindow?.dismiss()
            }
            popupWindow?.setOnDismissListener {

            }
        }
    }

    override var lastFragment: KClass<*>?
        get() = this::class
        set(value) {}

    /**
     * null param execute onbackPressed on Activity
     */
    override fun goBack() {
        mNavigator?.goTo(null)
    }

    /**
     * go to specific fragment
     */
    override fun goTo(fragmentClazz: KClass<*>?, param: Any?) {
        mNavigator?.goTo(fragmentClazz, param)
    }


}
