package com.example.iznajmljivanjevozila.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.data.User
import com.example.iznajmljivanjevozila.helpers.MockDataLoader

class Register : AppCompatActivity() {
    private lateinit var etFirstname: EditText
    private lateinit var etLastname: EditText
    private lateinit var etEmail: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister : Button
    private lateinit var tvCancel: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstname = findViewById(R.id.et_register_firstname)
        etLastname = findViewById(R.id.et_register_lastname)
        etEmail = findViewById(R.id.et_register_email)
        etUsername = findViewById(R.id.et_register_username)
        etPassword = findViewById(R.id.et_register_password)
        btnRegister = findViewById(R.id.btn_register)
        tvCancel = findViewById(R.id.tv_register_cancel)

        btnRegister.setOnClickListener{
            if(checkInput()){
                val newUser = User(
                    etFirstname.text.toString(),
                    etLastname.text.toString(),
                    etEmail.text.toString(),
                    etUsername.text.toString(),
                    etPassword.text.toString()
                )

                MockDataLoader.addUser(newUser)
                finish()
            }
        }

        tvCancel.setOnClickListener{
            finish()
        }

    }

    private fun checkInput(): Boolean{
        val firstname = etFirstname.text.toString()
        val lastname = etLastname.text.toString()
        val email = etEmail.text.toString()
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        if(firstname == "" ){
            showToast("Unesite ime.")
            return false
        }else if(lastname == ""){
            showToast("Unesite prezime.")
            return false
        }else if(!Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").matches(email)){
            showToast("Neispravna email adresa.")
            return false
        }else if(MockDataLoader.existsEmail(email)){
            showToast("Email adresa već postoji")
            return false
        }else if(username == ""){
            showToast("Unesite korisničko ime")
            return false
        }else if(MockDataLoader.existsUsername(username)){
            showToast("Korisničko ime već postoji")
            return false
        }else if(password == ""){
            showToast("Unesite lozinku")
            return false
        }
        return true
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}