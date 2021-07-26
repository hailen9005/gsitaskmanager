package com.gsi.tm.interfaces

import kotlin.reflect.KClass

interface INavigate {
    var lastFragment: KClass<*>?
    fun goBack()
    fun goTo(fragmentClazz: KClass<*>?, param: Any? = null)
}
