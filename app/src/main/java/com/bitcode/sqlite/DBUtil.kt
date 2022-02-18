package com.bitcode.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DBUtil private constructor(var context: Context) {

    private var db: SQLiteDatabase
    init {
        db = TasksHelper(context, "db_tasks", null, 1).writableDatabase
    }

    companion object {
        private var dbUtil : DBUtil? = null

        fun getInstance(context : Context) : DBUtil {
            if(dbUtil == null) {
                dbUtil = DBUtil(context)
            }
            return dbUtil!!
        }
    }

    fun getTasks(): ArrayList<Task>? {
        var c = db!!.rawQuery("select id, title, status from tasks order by id", null)

        var tasks = ArrayList<Task>()

        while (c.moveToNext()) {
            tasks.add(
                Task(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2)
                )
            )
        }

        c.close()
        return tasks
    }

    fun addTask(task: Task): Int {
        return -1
    }
}