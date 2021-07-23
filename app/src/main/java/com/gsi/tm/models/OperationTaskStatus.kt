package com.gsi.tm.models

import com.gsi.tm.enums.StateSend
import java.util.*

class OperationTaskStatus(val id: Long, var stateSend: StateSend, var date: Long) {


    var strDate: String? = "05/01/1960"
        get() {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date
            val d = calendar[Calendar.DAY_OF_MONTH]
            val m = calendar[Calendar.MONTH]
            val y = calendar[Calendar.YEAR]
            return "$d/$m/$y"
        }

}