package ufg.amb0.holamundointermedio

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var btnMensaje : Button
    private lateinit var txtEstudiante : TextView
    private lateinit var txtMateria : TextView
    private lateinit var txtAnioCiclo : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnMensaje = findViewById<Button>(R.id.btnMensaje);
        txtEstudiante = findViewById<TextView>(R.id.txtEstudiante);

        txtMateria = findViewById<TextView>(R.id.txtMateria);
        txtMateria.text = getString(R.string.subject_name);

        txtAnioCiclo = findViewById<TextView>(R.id.txtAnioCiclo)
        txtAnioCiclo.text = getString(R.string.studies_year);

        btnMensaje.setOnClickListener {
            Log.i("Test", "Testing log");
            txtEstudiante.text = getString(R.string.student_name)
            txtEstudiante.setTextColor(Color.RED);

            Log.i("onClick btnMensaje", "${getString(R.string.student_name)} -- ${Instant.now()}");
        }
    }

}