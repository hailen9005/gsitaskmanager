package com.gsi.tm.models

import com.gsi.tm.enums.SendState

data class OperationTaskStatus(
    val id: Long = -1,
    var sendState: SendState,
    var date: Long,
    val idTask: Long
)