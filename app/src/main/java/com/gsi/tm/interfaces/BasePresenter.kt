package com.gsi.tm.interfaces

import kotlin.reflect.KClass

interface BasePresenter<T> {

    fun onDestroy()
    fun onCreateView(mView: T)
}
