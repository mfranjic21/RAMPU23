package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iznajmljivanjevozila.SessionManager
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.helpers.MockDataLoader

class Login : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_login)
        tvRegister = findViewById(R.id.tv_login_register)
        etUsername = findViewById(R.id.et_login_username)
        etPassword = findViewById(R.id.et_login_password)

        btnLogin.setOnClickListener{
            loginUser(etUsername.text.toString(), etPassword.text.toString())
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username : String, password: String){
        val users = MockDataLoader.getUsers()

        if(users.any{user -> user.username == username && user.password == password}){
            SessionManager.setLoggedIn(true)

            for (user in users) {
                if (user.username == username && user.password == password) {
                    SessionManager.logginUser(user)
                }
            }

            showToast("Prijava uspješna")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            showToast("Prijava neuspješna. Molim provjerite podatke")
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}