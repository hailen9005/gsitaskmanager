package com.gsi.tm.helpers

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.R
import com.gsi.tm.helpers.App.getDrawableByState
import com.gsi.tm.interfaces.BasePresenter
import com.gsi.tm.interfaces.BaseView
import com.gsi.tm.interfaces.IGSISelectionTask
import com.gsi.tm.models.GSITaskDescription
import java.io.File
import java.io.FileReader
import java.lang.Exception
import kotlin.properties.Delegates

class MTaskManagerAdapter(val layoutInflater: LayoutInflater, val context: Context) :
    RecyclerView.Adapter<MTaskManagerAdapter.MviewHolder>() {

    var listener: IGSISelectionTask? = null
    var listTask = arrayListOf<GSITaskDescription>()

    init {

        listTask = App.getManagerDB(context)?.getListTasks() ?: arrayListOf()
    }


    inner class MviewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imvProfile: ImageView? = null
        var currentPosition by Delegates.observable(0) { property, oldValue, newValue -> }
        var task: Any?/* ImageLoader? */ = null

        /**
         * cancelTasks
         */
        fun cancelTasks() {
            //  task?.cancel(true)
            // Log.e("cancel", "" + adapterPosition + ": " + task?.isCancelled)
        }

        /**
         *  CreateTaskImageLoader
         */
        fun CreateTaskImageLoader(imFilePath: File, res: Int, bm: Bitmap?, imvS: ImageView) {
            //task = ImageLoader()
            // task?.execute(imFilePath, 0, null, imvS, context)
        }

        /**
         * createView
         */
        fun createView()/*: ImageLoader?*/: Any? {

            val position = adapterPosition
            Log.e(
                "positio",
                " " + position + " : " + this.currentPosition + " :" + this.position
            )

            val gsiTaskDescription = listTask[position]
            val dateStr = App.getDateFromMillis(gsiTaskDescription.date)
            val state = gsiTaskDescription.stateTask
            val drawableByState = context.getDrawableByState(state)
            imvProfile = this.itemView.findViewById(R.id.imv_profile_item_task)
            this.itemView.findViewById<TextView>(R.id.tv_title).text = gsiTaskDescription.tittle
            this.itemView.findViewById<TextView>(R.id.tv_date_item_task).text = dateStr
            this.itemView.findViewById<TextView>(R.id.tv_state).text = state.name
            this.itemView.findViewById<ImageView>(R.id.imv_state).setImageDrawable(drawableByState)
            this.itemView.setOnClickListener {
                listener?.onSelectectedTask(gsiTaskDescription)
            }
            //     CreateTaskImageLoader( , 0, null, imvProfile)
            return null
        }


    }


    override fun onViewRecycled(holder: MviewHolder) {
        super.onViewRecycled(holder)
        //bitmap.recycled
        holder.itemView.findViewById<ImageView>(R.id.imv_profile_item_task).setImageBitmap(null)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MviewHolder {
        val viewH = layoutInflater.inflate(R.layout.item_task, null)
        viewH.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val holder = MviewHolder(viewH)
        // holder.createView(holder)

        return holder
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onViewDetachedFromWindow(holder: MviewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.e("*", "onViewDetachedFromWindow" + holder.adapterPosition)
        holder.cancelTasks()
        holder.itemView.findViewById<ImageView>(R.id.imv_profile_item_task).setImageBitmap(null)
    }


    override fun onViewAttachedToWindow(holder: MviewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.createView()
        Log.e("*", "onViewAttachedFromWindow" + holder.adapterPosition)

    }

    override fun onBindViewHolder(holder: MviewHolder, position: Int) {
        holder.setIsRecyclable(false)
        // resetView(view, imv)
        //holder.createView()
    }


    var popudHtml: PopupWindow? = null

    private fun resetView(deviceview: View, imv: ImageView) {
        imv.setImageBitmap(null)
    }

}


