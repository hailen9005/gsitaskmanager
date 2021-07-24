package com.gsi.tm.interfaces

import kotlin.reflect.KClass

interface IBasePresenter<T> {

    fun onDestroy()
    fun onCreateView(mView: T)
}
