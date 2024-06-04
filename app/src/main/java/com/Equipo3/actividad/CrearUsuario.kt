package com.Equipo3.actividad

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CrearUsuario : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var newEmailEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var newSignUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_usuario)

        mAuth = FirebaseAuth.getInstance()

        newEmailEditText = findViewById(R.id.newEmailEditText)
        newPasswordEditText = findViewById(R.id.newPasswordEditText)
        newSignUpButton = findViewById(R.id.newSignUpButton)

        newSignUpButton.setOnClickListener { signUp() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun signUp() {
        val email = newEmailEditText.text.toString()
        val password = newPasswordEditText.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Ingrese los datos requeridos", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("CrearUsuario", "createUserWithEmail:success")
                    val user: FirebaseUser? = mAuth.currentUser
                    Toast.makeText(this, "Se registro correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w("CrearUsuario", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "No se pudo registrar", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
