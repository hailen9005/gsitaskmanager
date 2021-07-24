/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.interfaces

import com.gsi.tm.models.GSITaskDescription

interface IGSISelectionTask {
    fun onSelectectedTask(gsiTaskDescription: GSITaskDescription)
}