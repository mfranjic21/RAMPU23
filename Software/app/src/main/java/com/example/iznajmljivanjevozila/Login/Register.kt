package com.example.iznajmljivanjevozila.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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
            val firstName = etFirstname.text.toString()
            val lastName = etLastname.text.toString()
            val email = etEmail.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if(checkInput(firstName, lastName, email, username, password)){
                val auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                            val database = Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")

                            val usersRef = database.getReference("users")
                            val User = User(etFirstname.text.toString(), etLastname.text.toString(), etEmail.text.toString(),etUsername.text.toString(), etPassword.text.toString() )

                            val currentUser = auth.currentUser
                            usersRef.child(currentUser?.uid.toString()).setValue(User).addOnSuccessListener{
                                auth.signOut()
                                showToast("Uspješno ste se registrirali")
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener{
                                showToast("Neuspješno spremanje podataka u bazu")
                            }
                        }

                    }else{
                        showToast("Neuspješna registracija")
                    }

                }
            }
        }

        tvCancel.setOnClickListener{
            finish()
        }

    }

    private fun checkInput(firstName : String, lastName: String, email: String, username: String, password: String): Boolean{

        if(firstName == "" ){
            showToast("Unesite ime.")
            return false
        }else if(lastName == ""){
            showToast("Unesite prezime.")
            return false
        }else if(!Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").matches(email)){
            showToast("Neispravna email adresa.")
            return false
        }else if(username == ""){
            showToast("Unesite korisničko ime")
            return false
        }else if(password == ""){
            showToast("Unesite lozinku")
            return false
        }else if(password.length < 6){
            showToast("Duljina lozinke mora biti najmanje 6 znakova")
            return false
        }
        return true
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}