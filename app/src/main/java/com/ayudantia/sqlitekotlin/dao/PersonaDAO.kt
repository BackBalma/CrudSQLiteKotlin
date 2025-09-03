package com.ayudantia.sqlitekotlin.dao


import android.content.ContentValues
import android.content.Context
import com.ayudantia.sqlitekotlin.db.SQLiteHelper
import com.ayudantia.sqlitekotlin.model.Persona

class PersonaDAO(context: Context) {
    val helperDB: SQLiteHelper= SQLiteHelper(context)
    fun getAll(): List<Persona>{
        var personas = mutableListOf<Persona>()
        var db=helperDB.readableDatabase
        try {
            val cursor=db.rawQuery(
                "SELECT * FROM persona",
                null)
            cursor.use {
                while (it.moveToNext()){
                    val persona= Persona(
                        it.getInt(0),
                        it.getString(1),
                        it.getString(2)
                    )
                    personas.add(persona)
                }
            }
        }
        catch (e: Exception){
            throw RuntimeException(e.message)
        }
        finally {
            db.close()
        }
        return personas
    }
    fun create(persona: Persona){
        val datos = ContentValues()
        val db=helperDB.writableDatabase
        datos.put("nombre",persona.nombre)
        datos.put("apellido",persona.apellido)
        db.insert("persona",null,datos)
        db.close()
    }
    fun update(id:Int?, persona: Persona){
        val db = helperDB.writableDatabase
        val datos= ContentValues()
        datos.put("nombre",persona.nombre)
        datos.put("apellido",persona.apellido)
        val fila= db.update(
            "persona",
            datos,
            "id=?",
            arrayOf(id.toString())
        )
        db.close()
    }
    fun delete(id:Int?){
        val db=helperDB.writableDatabase
        db.delete(
            "persona",
            "id=?",
            arrayOf(id.toString())
        )
    }
}