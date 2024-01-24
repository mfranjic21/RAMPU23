package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.iznajmljivanjevozila.SessionManager
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil)

        val username = findViewById<TextView>(R.id.ime_prezime)
        val user = SessionManager.getLoggedUser()
        username.text = user.lastname+" "+user.firstname

        val vracaj = findViewById<ImageButton>(R.id.vrati_nazad)
        vracaj.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val postavkeProfila = findViewById<TextView>(R.id.promijeniProfil)
        postavkeProfila.setOnClickListener {
            val intent = Intent(this, ProfileConfiguration::class.java)
            startActivity(intent)
        }

        val odjava = findViewById<TextView>(R.id.odjava)
        odjava.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            SessionManager.setLoggedIn(false)
        }

        val podrska = findViewById<TextView>(R.id.podrska)
        podrska.setOnClickListener {
            val intent = Intent(this, CustomerService::class.java)
            startActivity(intent)
        }

        val postavkeAplikacije = findViewById<TextView>(R.id.promijeniPostavke)
        postavkeAplikacije.setOnClickListener {
            val intent = Intent(this, AppConfiguration::class.java)
            startActivity(intent)
        }

        val mojeRezervacije = findViewById<TextView>(R.id.mojeRezervacije)
        mojeRezervacije.setOnClickListener {
            val intent = Intent(this, MyReservations::class.java)
            startActivity(intent)
        }

    }
}