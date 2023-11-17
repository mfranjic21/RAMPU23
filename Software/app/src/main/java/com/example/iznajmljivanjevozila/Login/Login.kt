package com.example.iznajmljivanjevozila.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.helpers.MockDataLoader

class Login : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btn_login)
        etUsername = findViewById(R.id.et_login_username)
        etPassword = findViewById(R.id.et_login_password)

        btnLogin.setOnClickListener{
            LoginUser(etUsername.text.toString(), etPassword.text.toString())
        }
    }

    private fun LoginUser(username : String, password: String){
        val users = MockDataLoader.getUsers()

        if(users.any{user -> user.username == username && user.password == password}){
            SessionManager.setLoggedIn(true)

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