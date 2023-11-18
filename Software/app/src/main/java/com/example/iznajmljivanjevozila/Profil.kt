package com.example.iznajmljivanjevozila

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Profil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil)


        val vracaj = findViewById<ImageButton>(R.id.vrati_nazad)
        vracaj.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val postavkeProfila = findViewById<TextView>(R.id.promijeniProfil)
        postavkeProfila.setOnClickListener {
            val intent = Intent(this, PostavkeProfila::class.java)
            startActivity(intent)
        }

    }
}