package com.bitcode.sqlite

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class TasksProvider : ContentProvider() {

    private lateinit var db: SQLiteDatabase
    private var uriMatcher: UriMatcher

    companion object {
        val ALL_TASKS = 1
        val TASKS_BY_ID = 2
        val ALL_NOTES = 3
        val NOTES_BY_ID = 4
    }

    init {
        uriMatcher = UriMatcher(-1)
        uriMatcher.addURI("in.bitcode.tasks", "tasks", ALL_TASKS)
        uriMatcher.addURI("in.bitcode.tasks", "tasks/#", TASKS_BY_ID)
        uriMatcher.addURI("in.bitcode.tasks", "notes", ALL_NOTES)
        uriMatcher.addURI("in.bitcode.tasks", "notes/#", NOTES_BY_ID)
    }

    override fun onCreate(): Boolean {
        db = TasksHelper(
            context,
            "db_tasks",
            null,
            1
        ).writableDatabase
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        when(uriMatcher.match(uri)) {
            ALL_TASKS -> {
                return db.query("tasks", projection, selection, selectionArgs, null, null, sortOrder)
            }
            TASKS_BY_ID -> {
                return db.query(
                    "tasks",
                    projection,
                    "id = ?",
                    arrayOf(uri.pathSegments[1]),
                    null,
                    null,
                    sortOrder
                )
            }
            ALL_NOTES -> {
                return db.query("notes", projection, selection, selectionArgs, null, null, sortOrder)
            }
            NOTES_BY_ID -> {
                return db.query(
                    "tasks",
                    projection,
                    "id = ?",
                    arrayOf(uri.pathSegments[1]),
                    null,
                    null,
                    sortOrder
                )
            }
        }

        /*if( uri.pathSegments[0].equals("tasks") && uri.pathSegments.size == 1) {
            return db.query("tasks", projection, selection, selectionArgs, null, null, sortOrder)
        }
        if( uri.pathSegments[0].equals("tasks") && uri.pathSegments.size == 2) {
            return db.query(
                "tasks",
                projection,
                "id = ?",
                arrayOf(uri.pathSegments[1]),
                null,
                null,
                sortOrder
            )
        }

        if( uri.pathSegments[0].equals("notes") && uri.pathSegments.size == 1) {
            return db.query("notes", projection, selection, selectionArgs, null, null, sortOrder)
        }
        if( uri.pathSegments[0].equals("notes") && uri.pathSegments.size == 2) {
            return db.query("notes",
                projection,
                "id = ?",
                arrayOf(uri.pathSegments[1]),
                null,
                null,
                sortOrder
            )
        }*/

        return null
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        when(uriMatcher.match(uri)) {

            ALL_TASKS -> {
                var res = db.insert("tasks", null, values)
                if(res != -1L) {
                    return Uri.withAppendedPath(uri, "$res")
                }
            }
            ALL_NOTES -> {
                var res = db.insert("notes", null, values)
                if(res != -1L) {
                    return Uri.withAppendedPath(uri, "$res")
                }
            }
        }

        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}