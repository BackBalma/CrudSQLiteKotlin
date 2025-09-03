package com.ayudantia.sqlitekotlin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ayudantia.sqlitekotlin.dao.PersonaDAO
import com.ayudantia.sqlitekotlin.model.Persona

class MainActivity : AppCompatActivity() {
    lateinit var txtNombre: EditText
    lateinit var txtApellido: EditText
    lateinit var btnGuardar: Button
    lateinit var btnEliminar: Button
    lateinit var btnActualizar: Button
    lateinit var listPersona: ListView
    lateinit var dao: PersonaDAO
    private var idSeleccionado: Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //inicializamos los "componentes" y las clases
        inicializarComponentes()//Esto lo deje como funcion para que se vea bonito :3
        obtenerDatos()
        btnGuardar.setOnClickListener {
            if(txtNombre.text.isNotEmpty() && txtApellido.text.isNotEmpty())
            {
                agregarDatos(Persona(
                    null,
                    txtNombre.text.toString(),
                    txtApellido.text.toString()))
            }
            else
            {
                Toast.makeText(
                    this,
                    "No deje los registro en blanco",
                    Toast.LENGTH_SHORT)
                    .show()
            }
         }
        btnActualizar.setOnClickListener {
            if (idSeleccionado!=null)
            {
                editarDatos(
                    Persona(
                        idSeleccionado,
                        txtNombre.text.toString(),
                        txtApellido.text.toString()
                    ))
            }
            else
            {
                Toast.makeText(
                    this,
                    "Seleccione un registro",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
        btnEliminar.setOnClickListener {
            if(idSeleccionado!=null){
                eliminarDatos()
            }
            else{
                Toast.makeText(
                    this,
                    "Seleccione un registro",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
        listPersona.setOnItemClickListener{ _ , _ , position, _ ->
            limpiarEditTexts()
            val personaSeleccionada=listPersona.adapter.getItem(position)as Persona
            idSeleccionado=personaSeleccionada.id
            cambiarTxts(
                personaSeleccionada.nombre.toString(),
                personaSeleccionada.apellido.toString())
        }
    }
    fun limpiarEditTexts(){
        txtNombre.setText("")
        txtApellido.setText("")
        obtenerDatos()
        idSeleccionado=null
    }
    fun inicializarComponentes(){
        txtNombre=findViewById(R.id.txtNombre)
        txtApellido=findViewById(R.id.txtApellido)
        btnGuardar=findViewById(R.id.btnGuardar)
        btnEliminar=findViewById(R.id.btnEliminar)
        btnActualizar=findViewById(R.id.btnCambiar)
        listPersona=findViewById(R.id.listaPersonas)
        dao= PersonaDAO(this)
    }
    fun obtenerDatos(){
        var personas=dao.getAll()
        val adapter= ArrayAdapter(
            this,
            android.R.layout.preference_category,
            personas
        )
        listPersona.adapter=adapter
    }
    fun agregarDatos(persona: Persona){
        dao.create(persona)
        obtenerDatos()
    }
    fun eliminarDatos(){
        try {
            var id=idSeleccionado
            dao.delete(id)
            obtenerDatos()
        }
        catch (e: Exception){

        }
    }
    fun editarDatos(persona: Persona){
        var id=idSeleccionado
        dao.update(id,persona)
        limpiarEditTexts()

    }
    fun cambiarTxts(texto1:String,texto2:String){
        txtNombre.setText(texto1)
        txtApellido.setText(texto2)
    }

}