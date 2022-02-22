package com.bitcode.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class TasksHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table tasks(id integer primary key, title text, status integer)")
        insertTasks(db!!)

        db!!.execSQL("create table notes(id integer primary key, body text)")
        insertNotes(db!!)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    private fun insertNotes(db: SQLiteDatabase) {

        var values = ContentValues()
        values.put("id", 9090)
        values.put("body", "THis is a sample note....")
        db.insert(
            "notes",
            null,
            values
        )

        values.put("id", 9091)
        values.put("body", "android note....")
        db.insert(
            "notes",
            null,
            values
        )

        values.put("id", 9092)
        values.put("body", "Kotlin note....")
        db.insert(
            "notes",
            null,
            values
        )
    }

    private fun insertTasks(db: SQLiteDatabase) {
        var values = ContentValues()

        values.put("id", 1001)
        values.put("title", "Complete the sprint asap...")
        values.put("status", 0)

        //var rowNum = db.insert("tasks", "category, due_date", values)
        var rowNum = db.insert("tasks", null, values)
        Log.e("db", "rownum $rowNum")

        values.put("id", 1002)
        values.put("title", "Get a job")
        values.put("status", 0)

        //var rowNum = db.insert("tasks", "category, due_date", values)
        rowNum = db.insert("tasks", null, values)
        Log.e("db", "rownum $rowNum")

        values.put("id", 1003)
        values.put("title", "Pay bills and somethings else")
        values.put("status", 1)

        //var rowNum = db.insert("tasks", "category, due_date", values)
        rowNum = db.insert("tasks", null, values)
        Log.e("db", "rownum $rowNum")

    }
}