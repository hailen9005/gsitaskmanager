/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.models

class MOption<T, E, U>(val column: String, val operator: String? = "=", val value: U)
