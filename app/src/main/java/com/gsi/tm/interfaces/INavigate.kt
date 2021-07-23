package com.gsi.tm.interfaces

import kotlin.reflect.KClass

interface INavigate {
    fun setFragment(fragmentClazz: KClass<*>, param: Any?)
}
