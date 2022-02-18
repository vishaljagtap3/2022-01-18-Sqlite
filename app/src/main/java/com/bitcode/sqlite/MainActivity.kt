package com.bitcode.sqlite

import android.app.Activity
import android.content.ContentValues
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var db : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*db = openOrCreateDatabase(
            "db_tasks",
            Activity.MODE_PRIVATE,
            null,
            DatabaseErrorHandler {
            }
        )*/

        db = TasksHelper(
            this,
            "db_tasks",
            null,
            1
        ).writableDatabase

        //createTables()
        insertTasks()
        fetchData()
        mt("-------------------------------------")
        updateData()
        mt("-------------------------------------")
        fetchData()
        mt("-------------------------------------")
        deleteData()
        fetchData()


        mt("-------------------------------------")
        mt("-------------------------------------")

        var dbUtil = DBUtil.getInstance(this)
        for(task in dbUtil.getTasks()!!) {
            Log.e("db", task.toString())
        }


    }

    private fun deleteData() {
        var count = db.delete(
            "tasks",
            "id = ?",
            arrayOf("-1")
        )
        mt("deleted $count")
    }

    private fun updateData() {
        var values = ContentValues()
        values.put("title", "Updated task *****")
        values.put("status", 0)

        var count = db.update(
            "tasks",
            values,
            "id = ?",
            arrayOf("-1")
        )
        mt("updated $count")
    }

    private fun mt(text : String) {
        Log.e("db", text)
    }

    private fun fetchData() {
        /*var columns = arrayOf("id", "title", "status")
        var where = "id > ? and status = ?"
        var whereArgs = arrayOf("1002", "1")
        db.query(
            "tasks",
            columns,
            where,
            whereArgs,
            "priority",
            "count(*) > 4",
            "status asc, id desc"
        )*/

        /*var c : Cursor = db.query(
            "tasks",
            null,
            null,
            null,
            null,
            null,
            "title desc"
        )*/
        var c = db.rawQuery("select id, title, status from tasks order by id", null)

        while (c.moveToNext()) {
            var task = Task(
                c.getInt(0),
                c.getString(1),
                c.getInt(2)
            )
            Log.e("db", "${task.toString()}")
        }

        c.close()
    }

    private fun insertTasks() {
        var values = ContentValues()

        values.put("id", 1001)
        values.put("title", "Complete the sprint asap...")
        values.put("status", 0)

        //var rowNum = db.insert("tasks", "category, due_date", values)
        var rowNum = db.insert("tasks", null, values)
        Log.e("db", "rownum $rowNum")

        values.put("id", -1)
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

    private fun createTables() {
        try {
            db.execSQL("create table tasks(id integer primary key, title text, status integer)")
            /*db.execSQL(
                "create table messages(? integer primary key, ? text)",
                arrayOf("id", "body")
            )*/
        }
        catch (e : Exception) {
        }

    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}