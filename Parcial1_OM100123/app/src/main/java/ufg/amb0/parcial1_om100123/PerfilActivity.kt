package ufg.amb0.parcial1_om100123

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val layout = findViewById<LinearLayout>(R.id.layoutPerfil)
        val tvResumen = findViewById<TextView>(R.id.tvResumen)
        val btnEditar = findViewById<Button>(R.id.btnEditar)

        val nombre = intent.getStringExtra("nombre")
        val edad = intent.getIntExtra("edad", 0)
        val genero = intent.getStringExtra("genero")
        val categoria = intent.getStringExtra("categoria")

        tvResumen.text = buildString {
            append("Hola ")
            append(nombre)
            append(", eres un ")
            append(categoria)
            append(" de género ")
            append(genero)
            append(".")
        }

        when (categoria) {
            "Niño/a" -> layout.setBackgroundColor(Color.CYAN)
            "Adolescente" -> layout.setBackgroundColor(Color.YELLOW)
            "Adulto" -> layout.setBackgroundColor(Color.GREEN)
            "Adulto Mayor" -> layout.setBackgroundColor(Color.GRAY)
        }

        btnEditar.setOnClickListener {
            finish()
        }
    }
}