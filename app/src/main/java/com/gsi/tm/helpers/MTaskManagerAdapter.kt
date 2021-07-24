package com.gsi.tm.helpers

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.interfaces.IOnItemAdapter
import java.io.File
import kotlin.properties.Delegates

class MTaskManagerAdapter<T>(
    val layoutInflater: LayoutInflater,
    val context: Context,
    val listTask: ArrayList<T> = arrayListOf(),
    val resId: Int,
    var listener: IOnItemAdapter? = null
) :
    RecyclerView.Adapter<MTaskManagerAdapter<T>.MviewHolder>() {

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

            listener?.constructView(listTask[position], this.itemView)
            //     CreateTaskImageLoader( , 0, null, imvProfile)
            return null
        }


    }


    override fun onViewRecycled(holder: MviewHolder) {
        super.onViewRecycled(holder)
        //bitmap.recycled
        //holder.itemView.findViewById<ImageView>(R.id.imv_profile_item_task).setImageBitmap(null)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MviewHolder {
        val viewH = layoutInflater.inflate(resId, null)
        viewH.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val holder = MviewHolder(viewH)
        return holder
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onViewDetachedFromWindow(holder: MviewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.e("*", "onViewDetachedFromWindow" + holder.adapterPosition)
        holder.cancelTasks()
        listener?.detachedFromWindow(holder.itemView)
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

    private fun resetView(deviceview: View, imv: ImageView) {
        imv.setImageBitmap(null)
    }

}


