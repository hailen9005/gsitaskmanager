package com.gsi.tm.helpers

import android.content.Context
import android.os.Environment
import android.widget.EditText
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
}