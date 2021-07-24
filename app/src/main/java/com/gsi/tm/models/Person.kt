package com.gsi.tm.models

open class Person(
    val id: Long = -1,
    val fullName: String,
    val occupation: String,
    val globalId: String,
    val typeProfile: String,
    val isAccountLocal: Boolean
)