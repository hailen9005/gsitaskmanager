package com.gsi.tm.interfaces

import kotlin.reflect.KClass

interface INavigate {
    fun goBack()
    fun goTo(fragmentClazz: KClass<*>?, param: Any? = null)
}
