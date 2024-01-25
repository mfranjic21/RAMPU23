package com.example.iznajmljivanjevozila.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_login)
        tvRegister = findViewById(R.id.tv_login_register)
        etEmail = findViewById(R.id.et_login_email)
        etPassword = findViewById(R.id.et_login_password)

        btnLogin.setOnClickListener{
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if(email == "" || password == ""){
                showToast("Niste ispunili polja")
            }else {
                auth = FirebaseAuth.getInstance()
                loginUser(email, password)
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email : String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            showToast("Uspješna prijava")
            val intent = Intent(this, MainActivity :: class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            showToast("Neuspješna prijava")
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}