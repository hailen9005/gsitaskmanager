package com.gsi.tm.helpers

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsi.tm.R
import com.gsi.tm.enums.StateTask
import com.gsi.tm.fragments.manager.ManagerFragment
import com.gsi.tm.fragments.team_manager.TeamManagerFragment
import com.gsi.tm.fragments.team_member.TeamMemberFragment
import com.gsi.tm.interfaces.IOnItemAdapter
import com.gsi.tm.models.Manager
import com.gsi.tm.models.Person
import com.gsi.tm.models.TeamManager
import com.gsi.tm.models.TeamMember
import layout.AccountSelectFragment
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

    private var managerDB: DBManager? = null
    var profileUser: Person? = null

    /**
     * Database location
     */
    fun Context.getDBPath(): String {
        val dir =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) externalCacheDir?.path else cacheDir.path
        return dir.plus("/mdb.db")
    }


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
        return (!(this.text.toString().contains(Regex("\\W"))))
    }

    fun getDateFromMillis(dateMillis: Long, withHour: Boolean = false): String {
        val caleder = Calendar.getInstance()
        caleder.timeInMillis = dateMillis
        val d = caleder[Calendar.DAY_OF_MONTH]
        val m = caleder[Calendar.MONTH]
        val y = caleder[Calendar.YEAR]
        val h = caleder[Calendar.HOUR]
        val min = caleder[Calendar.MINUTE]
        val apm = caleder[Calendar.AM_PM]

        val hour = if (withHour) "$h:$min $apm" else ""
        val dateStr = "$d/$m/$y $hour"
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
        val point = Point()
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
        gravity: Int = Gravity.CENTER
    ): PopupWindow {
        val sc = getScreenSize(this)
        val width = size?.x ?: (70 + sc.x / 3)
        val height = size?.y ?: (sc.y - sc.y / 3)
        val popupWindow =
            PopupWindow(containerView, width, height)
        containerView.layoutParams = LinearLayout.LayoutParams(width, height)
        //popupWindow.setBackgroundDrawable()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 100f
        }
        popupWindow.showAtLocation(
            onView,
            gravity,
            location?.x ?: -10,
            location?.y ?: 5
        )
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 100f
        }

        return popupWindow
    }

    fun Context.createRecyclerView(): RecyclerView {
        val inflater = LayoutInflater.from(this)
        val recyclerView: RecyclerView =
            inflater.inflate(R.layout.view_recycler, null) as RecyclerView
        val lymanager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = lymanager
        recyclerView.setItemViewCacheSize(60)
        recyclerView.setHasFixedSize(true)
        return recyclerView
    }

    inline fun <reified T> RecyclerView.setAdaterToRecyclerView(
        listTask: ArrayList<T>,
        listener: IOnItemAdapter,
        resId: Int,
        contx: Context
    ) {
        val inflater = LayoutInflater.from(contx)
        val mtaskAdapter =
            MTaskManagerAdapter(inflater, contx, listTask, resId, listener)
        if (this.adapter is MTaskManagerAdapter<*>)
            (this.adapter as? MTaskManagerAdapter<*>)?.listener = null
        this.adapter = null
        this.adapter = mtaskAdapter
    }

    fun actionByTypeProfile(person: Person) {
        val targetFragment = when (person) {
            is Manager -> ManagerFragment::class
            is TeamManager -> TeamManagerFragment::class
            is TeamMember -> TeamMemberFragment::class
            else -> AccountSelectFragment::class
        }
    }
}