package ufg.amb0.parcial1_om100123

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch

class MainActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtEdad: EditText
    private lateinit var spGenero: Spinner
    private lateinit var chkTerminos: CheckBox
    private lateinit var swRecordar: MaterialSwitch
    private lateinit var btnRegistrar: Button
    private lateinit var btnVerPerfil: Button

    private val PREFS = "PerfilPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtNombre = findViewById(R.id.txtNombre)
        txtEdad = findViewById(R.id.txtEdad)
        spGenero = findViewById(R.id.spGenero)
        chkTerminos = findViewById(R.id.chkTerminos)
        swRecordar = findViewById(R.id.swRecordar)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnVerPerfil = findViewById(R.id.btnVerPerfil)

        val generos = arrayOf("Masculino", "Femenino", "Otro")
        spGenero.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, generos)

        btnRegistrar.setOnClickListener {
            registrar()
        }

        btnVerPerfil.setOnClickListener {
            cargarPerfilGuardado()
        }
    }

    private fun registrar() {

        var valido = true

        val nombre = txtNombre.text.toString().trim()
        val edadStr = txtEdad.text.toString().trim()
        val genero = spGenero.selectedItem.toString()

        if (nombre.isEmpty()) {
            txtNombre.error = "Nombre requerido"
            valido = false
        }

        val edad = edadStr.toIntOrNull()
        if (edad == null || edad !in 1..120) {
            txtEdad.error = "Edad entre 1 y 120"
            valido = false
        }

        if (!chkTerminos.isChecked) {
            chkTerminos.error = "Debe aceptar términos"
            valido = false
        }

        if (!valido) return

        val categoria = when (edad!!) {
            in 0..12 -> "Niño/a"
            in 13..17 -> "Adolescente"
            in 18..59 -> "Adulto"
            else -> "Adulto Mayor"
        }

        if (swRecordar.isChecked) {
            val prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            prefs.edit()
                .putString("nombre", nombre)
                .putInt("edad", edad)
                .putString("genero", genero)
                .apply()
        }

        val intent = Intent(this, PerfilActivity::class.java)
        intent.putExtra("nombre", nombre)
        intent.putExtra("edad", edad)
        intent.putExtra("genero", genero)
        intent.putExtra("categoria", categoria)
        startActivity(intent)
    }

    private fun cargarPerfilGuardado() {
        val prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        if (!prefs.contains("nombre")) {
            Toast.makeText(this, "No hay datos guardados", Toast.LENGTH_SHORT).show()
            return
        }

        txtNombre.setText(prefs.getString("nombre", ""))
        txtEdad.setText(prefs.getInt("edad", 0).toString())
        val genero = prefs.getString("genero", "")
        val posicion = (spGenero.adapter as ArrayAdapter<String>).getPosition(genero)
        spGenero.setSelection(posicion)
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "Aplicación en segundo plano", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Bienvenido nuevamente", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Salir")
            .setMessage("¿Desea salir?")
            .setPositiveButton("Sí") { _, _ -> super.onBackPressed() }
            .setNegativeButton("No", null)
            .show()
    }
}