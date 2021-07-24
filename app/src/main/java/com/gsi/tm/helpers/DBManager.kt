/*
 * Copyright (c) Desarrollado por Hailen Baez,
 */

/**desarollado por Hailen Baez
 */

package com.gsi.tm.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.gsi.tm.enums.StateSend
import com.gsi.tm.helpers.App.getDBPath
import com.gsi.tm.models.GSITaskDescription
import com.gsi.tm.models.OperationTaskStatus
import com.gsi.tm.models.Person

class DBManager(context: Context) : SQLiteOpenHelper(context, context.getDBPath(), null, 1) {
    val TAB_PERSON: String = "person"
    val TAB_TASK: String = "task"
    val TAB_TASK_STATUS: String = "operationTaskStatus"

    private val CREATE_TABLE_PERSON: String =
        "Create table if not exists $TAB_PERSON ( id INTEGER PRIMARY KEY AUTOINCREMENT , fullName TEXT, occupation TEXT, other TEXT  )"

    private val CREATE_TABLE_TASK_STATUS: String =
        "Create table if not exists $TAB_TASK_STATUS ( id INTEGER PRIMARY KEY AUTOINCREMENT , stateSend TEXT, date Long , idTask, FOREIGN KEY(idTask) REFERENCES $TAB_TASK(id) )"

    private val CREATE_TABLE_TASK: String =
        "Create table if not exists $TAB_TASK ( id INTEGER PRIMARY KEY AUTOINCREMENT , tittle TEXT, description TEXT, project TEXT, type TEXT, responsible LONG, author TEXT, date LONG, state TEXT, idPerson, FOREIGN KEY(idPerson) REFERENCES $TAB_PERSON(id)   )"
    var dbm: SQLiteDatabase


    init {
        ///dbm = SQLiteDatabase.openOrCreateDatabase("mdb.db",null)
        dbm = this.writableDatabase

    }

    override fun onCreate(db: SQLiteDatabase?) {
        dbm = db!!
        dbm.execSQL(CREATE_TABLE_PERSON)
        dbm.execSQL(CREATE_TABLE_TASK)
        dbm.execSQL(CREATE_TABLE_TASK_STATUS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    /**
     * Generic insert
     */
    inline fun <reified T> insert(el: T) {
        val cValues = ContentValues()
        var columns = ""
        var currentTab = ""

        when (T::class) {
            Person::class -> {
                val person: Person = el as Person
                columns = "fullName, occupation, other"
                currentTab = TAB_PERSON
                cValues.put("fullName", person.fullName)
                cValues.put("occupation", person.occupation)
                cValues.put("other", person.other)
            }

            OperationTaskStatus::class -> {
                val opTaskStatus: OperationTaskStatus = el as OperationTaskStatus
                columns = "stateSend, date"
                currentTab = TAB_PERSON
                cValues.put("stateSend", opTaskStatus.stateSend.name)
                cValues.put("date", opTaskStatus.date)
            }

            GSITaskDescription::class -> {
                val gsiTaskDescript: GSITaskDescription = el as GSITaskDescription
                columns = "tittle, description, type ,state, date , author,responsible, project"
                currentTab = TAB_TASK
                cValues.put("tittle", gsiTaskDescript.tittle)
                cValues.put("description", gsiTaskDescript.description)
                cValues.put("type", gsiTaskDescript.type)
                cValues.put("state", gsiTaskDescript.state)
                cValues.put("date", gsiTaskDescript.date)
                cValues.put("author", gsiTaskDescript.author)
                cValues.put("responsible", gsiTaskDescript.responsible)
                cValues.put("project", gsiTaskDescript.project)
            }
            else -> {
                throw(Exception("Clase sin checkear insert::DB!! ${T::class}"))
            }
        }

        try {
            dbm = this.writableDatabase
            dbm.beginTransaction()
            dbm.insert(currentTab, columns, cValues)
            dbm.setTransactionSuccessful()
            dbm.endTransaction()

        } catch (ex: Exception) {
            Log.e("dbm error", "" + ex.message)
        } finally {
        }

    }


    /**
     *  update TAB_TASK_STATUS
     */
    fun updateStateOperationStatus(operationTaskStatus: OperationTaskStatus) {
        val cValues = ContentValues()
        val stateValue = operationTaskStatus.stateSend.name
        cValues.put("state", stateValue)
        dbm.beginTransaction()
        dbm.update(TAB_TASK_STATUS, cValues, "state=?", arrayOf(stateValue))
        dbm.setTransactionSuccessful()
        dbm.endTransaction()
    }


    /**
     *  update TAB_TASK_STATUS
     */
    fun updateStateTask(gsiTaskDescription: GSITaskDescription) {
        val cValues = ContentValues()
        val stateValue = gsiTaskDescription.state
        cValues.put("state", stateValue)
        dbm.beginTransaction()
        dbm.update(TAB_TASK, cValues, "id=?", arrayOf(gsiTaskDescription.id.toString()))
        dbm.setTransactionSuccessful()
        dbm.endTransaction()
    }


    /**
     * get List Persons
     */
    fun getListPersons(idPerson: String? = null): ArrayList<Person> {
        val personList = arrayListOf<Person>()
        dbm = this.readableDatabase
        val WHERE_CLAUSE = when {
            idPerson != null -> " where id='$idPerson'"
            else -> ""
        }

        val cursor =
            dbm.rawQuery("select * FROM $TAB_PERSON $WHERE_CLAUSE ", null)
        cursor?.let {
            while (cursor.moveToNext()) {
                val id = it.getLong(0)
                val fullName = it.getString(1)
                val occupation = it.getString(2)
                val other = it.getString(3)
                val person = Person(id, fullName, occupation, other)
                personList.add(person)
            }
        }
        cursor?.close()
        return personList
    }

    /**
     * get OperationStatus
     */
    fun getListOperationStatus(idOperation: String? = null): ArrayList<OperationTaskStatus> {
        val opStatusList = arrayListOf<OperationTaskStatus>()
        dbm = this.readableDatabase
        val WHERE_CLAUSE = when {
            idOperation != null -> " where id='$idOperation'"
            else -> ""
        }

        val cursor =
            dbm.rawQuery("select * FROM $TAB_TASK_STATUS $WHERE_CLAUSE ", null)
        cursor?.let {
            while (cursor.moveToNext()) {
                val id = it.getLong(0)
                val status = it.getString(1)
                val date = it.getLong(2)

                val opStatus = OperationTaskStatus(id, StateSend.valueOf(status), date)
                opStatusList.add(opStatus)
            }
        }
        cursor?.close()
        return opStatusList
    }


    /**
     *
     */
    fun getListTasks(idTask: String? = null): ArrayList<GSITaskDescription> {
        val gsiTaskList = arrayListOf<GSITaskDescription>()
        dbm = this.readableDatabase
        val WHERE_CLAUSE = when {
            idTask != null -> " where id ='$idTask'"
            else -> ""
        }

        val cursor =
            dbm.rawQuery("select * FROM $TAB_TASK $WHERE_CLAUSE ", null)
        cursor?.let {
            while (cursor.moveToNext()) {
                val id = it.getLong(0)
                val tittle = it.getString(1)
                val description = it.getString(2)
                val project = it.getString(3)
                val type = it.getString(4)
                val responsible = it.getLong(5)
                val author = it.getString(6)
                val date = it.getLong(7)
                val state = it.getString(8)

                val gsiTaskDescript = GSITaskDescription(
                    id,
                    tittle = tittle,
                    description = description,
                    project = project,
                    type = type,
                    responsible = responsible,
                    author = author,
                    date = date,
                    state = state
                )
                gsiTaskList.add(gsiTaskDescript)
            }
        }
        cursor?.close()
        return gsiTaskList
    }

    /**
     * delete from DB entity
     */
    inline fun <reified T> delete(cl: T) {
        when (T::class) {
            GSITaskDescription::class -> {
                val gsiTask = (cl as GSITaskDescription)
                val idLong = gsiTask.id.toString()
                dbm.delete(TAB_TASK, "id", arrayOf(idLong))
            }

            Person::class -> {
                val person = (cl as Person)
                val idLong = person.id.toString()
                dbm.delete(TAB_TASK, "id", arrayOf(idLong))
            }

            OperationTaskStatus::class -> {
                val opStatus = (cl as OperationTaskStatus)
                val idLong = opStatus.id.toString()
                dbm.delete(TAB_TASK, "id", arrayOf(idLong))
            }
            else -> {
                throw(Exception("Clase sin checkear  delete:: DB!! ${T::class}"))
            }
        }
    }

}
