package com.example.iznajmljivanjevozila

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val korisnickaPodrskaButton = findViewById<Button>(R.id.korisnickaPodrska)

        korisnickaPodrskaButton.setOnClickListener {

            val intent = Intent (this, KorisnickaPodrska::class.java)
            startActivity(intent)
        }
    }
}