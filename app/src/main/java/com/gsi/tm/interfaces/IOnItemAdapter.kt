/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.interfaces

import android.view.View
import com.gsi.tm.models.GSITaskDescription

interface IOnItemAdapter {
    fun <T> onSelectectedItem(entity: T)
    fun detachedFromWindow(itemView: View)
    fun <T> constructView(entity: T, itemView: View)
}