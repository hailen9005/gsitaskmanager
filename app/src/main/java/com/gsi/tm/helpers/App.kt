package com.gsi.tm.helpers

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import com.gsi.tm.R
import com.gsi.tm.enums.StateTask
import java.util.*

object App {

    /*
    *   Firebase
    */
    val TOPIC = "manger"
    val FIREBASE_SERVER = "https://fcm.googleapis.com/fcm/send"
    val SERVER_KEY =
        "AAAAWbh6anY:APA91bEgi062y4Dhh9DXFAm_WAfK9SprH4ryqxZshFZ79_aRULTuQxDcy-2QVt_6a-C2RSc9Xp4B2pKR17rVvkT5s8LXewIdgX-lFfZT1YqubeYLmxoHW2Urp8uirwn9l0WRUYXgdmxz"
    val ID_REMITENTE = "385347119734"

    /**
     * Database location
     */
    fun Context.getDBPath(): String {
        val dir =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) externalCacheDir?.path else cacheDir.path
        return dir.plus("/mdb.db")
    }

    private var managerDB: DBManager? = null

    fun getManagerDB(context: Context): DBManager? {
        managerDB?.let { } ?: kotlin.run {
            managerDB = DBManager(context)
        }
        return managerDB
    }

    fun EditText.requireLength(size: Int = -1): Boolean {
        return ((this.text.toString().length >= size))
    }

    fun EditText.isValidNameChars(): Boolean {
        return ((this.text.toString().contains(Regex("\\W"))))
    }

    fun getDateFromMillis(dateMillis: Long): String {
        val caleder = Calendar.getInstance()
        caleder.timeInMillis = dateMillis
        val d = caleder[Calendar.DAY_OF_MONTH]
        val m = caleder[Calendar.MONTH]
        val y = caleder[Calendar.YEAR]
        val h = caleder[Calendar.HOUR]
        val min = caleder[Calendar.MINUTE]
        val apm = caleder[Calendar.AM_PM]

        val dateStr = "$d/$m/$y $h:$min $apm"
        return dateStr
    }

    fun Context.getDrawableByState(state: StateTask): Drawable? {
        val resId = when (state) {
            StateTask.New -> R.drawable.draw_new_state
            StateTask.Open -> R.drawable.draw_open_state
            StateTask.InProgress -> R.drawable.draw_inprogress_state
            StateTask.Closed -> R.drawable.draw_closed_state
            StateTask.OffTime -> R.drawable.draw_offtime_state
            else -> R.drawable.draw_undefined_state
        }

        return AppCompatResources.getDrawable(this, resId)
    }

    /**
     * getScreenSize
     */
    fun getScreenSize(context: Context): Point {
        val windowManager: WindowManager =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = windowManager.defaultDisplay
        var point = Point()
        display.getSize(point)
        return point
    }


    /**
     * showPopud pass a view
     */
    fun Context.showPopud(
        containerView: View,
        onView: View,
        size: Point? = null,
        location: Point? = null,
        gravity: Int = Gravity.END
    ): PopupWindow {
        val sc = App.getScreenSize(this)
        val popupWindow =
            PopupWindow(containerView, size?.x ?: (70 + sc.x / 3), size?.y ?: (sc.y - sc.y / 3))
        //containerView.layoutParams = LinearLayout.LayoutParams(sc.x / 3, sc.y - 200)
        //popupWindow.setBackgroundDrawable()
        popupWindow.showAtLocation(
            onView,
            gravity,
            location?.x ?: -10,
            location?.y ?: 5
        )

        popupWindow.setOnDismissListener { }
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 100f
        }

        return popupWindow
    }
}