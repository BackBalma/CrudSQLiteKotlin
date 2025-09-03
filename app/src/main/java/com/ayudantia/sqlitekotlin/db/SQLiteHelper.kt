package com.ayudantia.sqlitekotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context):
    SQLiteOpenHelper(
        context,
        "ayudantia.db",
        null,
        1
    )
{
    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreate="CREATE TABLE persona(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, apellido TEXT);"
        db!!.execSQL(sqlCreate)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        //No implementado para este ejemplo ;D
    }
}