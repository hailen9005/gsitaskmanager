package com.gsi.tm.models

import com.gsi.tm.enums.StateTask
import kotlin.properties.Delegates

class GSITaskDescription(
    val id: Long = -1,
    val tittle: String,
    val description: String,
    val type: String,
    val responsible: Long,
    val project: String,
    val author: String,
    var date: Long,
    var state: String = StateTask.New.name
) {
    var stateTask: StateTask by Delegates.observable(StateTask.New) { property, oldValue, newValue ->
        state = newValue.name
    }


}
