package ufg.amb0.parcial1_om100123

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch

class MainActivity : AppCompatActivity() {

    private lateinit var tvSaludo: TextView
    private lateinit var txtNombre: EditText
    private lateinit var txtEdad: EditText
    private lateinit var btnSaludo: Button
    private lateinit var btnLimpiar: Button
    private lateinit var swRecordar: MaterialSwitch

    private val PREFS_NAME = "MisPreferencias"
    private val KEY_NOMBRE = "nombre_guardado"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvSaludo = findViewById(R.id.tvSaludo)
        txtNombre = findViewById(R.id.txtNombre)
        txtEdad = findViewById(R.id.txtEdad)
        btnSaludo = findViewById(R.id.btnSaludo)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        swRecordar = findViewById(R.id.swRemember)

        cargarNombreGuardado()

        btnSaludo.setOnClickListener {
            if (validateTextFields(txtNombre, txtEdad)) {

                val nombre = txtNombre.text.toString().trim()
                val edad = txtEdad.text.toString().toInt()

                tvSaludo.text = "¡Hola, $nombre! Tienes $edad años."

                // Cambio de color según edad
                when {
                    edad < 18 -> tvSaludo.setTextColor(Color.BLUE)
                    edad in 18..50 -> tvSaludo.setTextColor(Color.GREEN)
                    else -> tvSaludo.setTextColor(Color.RED)
                }

                // Guardar nombre si switch está activado
                if (swRecordar.isChecked) {
                    guardarNombre(nombre)
                }
            }
        }

        btnLimpiar.setOnClickListener {
            limpiarCampos()
        }
    }

    private fun validateTextFields(txtNombre: EditText, txtEdad: EditText): Boolean {

        val nombreValue = txtNombre.text.toString().trim()
        val edadValue = txtEdad.text.toString().trim()

        if (nombreValue.isEmpty()) {
            txtNombre.error = "El nombre no puede estar vacío"
            return false
        }

        if (edadValue.isEmpty() || edadValue.toIntOrNull() == null || edadValue.toInt() <= 0) {
            txtEdad.error = "Debe ingresar una edad válida"
            return false
        }

        return true
    }

    private fun guardarNombre(nombre: String) {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_NOMBRE, nombre).apply()
    }

    private fun cargarNombreGuardado() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val nombreGuardado = prefs.getString(KEY_NOMBRE, null)

        if (nombreGuardado != null) {
            txtNombre.setText(nombreGuardado)
            swRecordar.isChecked = true
        }
    }

    private fun limpiarCampos() {
        txtNombre.text.clear()
        txtEdad.text.clear()
        tvSaludo.text = ""
        tvSaludo.setTextColor(Color.BLACK)

        swRecordar.isChecked = false

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_NOMBRE).apply()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Salir")
            .setMessage("¿Desea salir de la aplicación?")
            .setPositiveButton("Sí") { _, _ -> super.onBackPressed() }
            .setNegativeButton("No", null)
            .show()
    }
}
