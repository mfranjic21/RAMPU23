package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.SessionManager
import com.example.iznajmljivanjevozila.helpers.MockDataLoader
import com.example.iznajmljivanjevozila.data.User


class ProfileConfiguration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.postavke_profila)

        val user = SessionManager.getLoggedUser()

        val firstname = findViewById<TextView>(R.id.promijeni_ime)
        firstname.text = user.firstname

        val lastname = findViewById<TextView>(R.id.promijeni_prezime)
        lastname.text = user.lastname

        val email = findViewById<TextView>(R.id.promjeniEmail)
        email.text = user.email

        val username = findViewById<TextView>(R.id.promijeni_user)
        username.text = user.username

        val password = findViewById<TextView>(R.id.promijeni_pass)
        password.text = user.password

        val vracaj = findViewById<ImageButton>(R.id.vrati_nazad_promjena)
        vracaj.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }


        val spremi = findViewById<Button>(R.id.spremi)

        spremi.setOnClickListener {

            val updatedUser = User(
                firstname.text.toString(),
                lastname.text.toString(),
                email.text.toString(),
                username.text.toString(),
                password.text.toString()
            )

            SessionManager.logginUser(updatedUser)
            MockDataLoader.updateUser(updatedUser, user.username)

            Toast.makeText(applicationContext,"Uspješno spremljeno",Toast.LENGTH_SHORT).show()
        }

    }

}