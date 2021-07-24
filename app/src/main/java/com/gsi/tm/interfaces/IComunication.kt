/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

package com.gsi.tm.interfaces

import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.Person

interface IComunication {

    fun registerPerson(person: Person)
    fun login(gsiTaskDescription: GSITaskDescription)
    fun receiveTask(gsiTaskDescription: GSITaskDescription)
    fun sendNewTask(
        gsiTaskDescription: GSITaskDescription,
        person: Person,
        fn: (success: Boolean, error: String?) -> Unit
    )

    /*
    fun sendNewTaskToTeamManager(gsiTaskDescription: GSITaskDescription , personId: Long)
    fun sendNewTaskToTeamMember(gsiTaskDescription: GSITaskDescription  , personId: Long)
    fun sendUpdateTaskToTeamMember(gsiTaskDescription: GSITaskDescription , personId: Long)*/
}