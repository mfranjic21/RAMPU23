package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val korisnickaPodrskaButton = findViewById<ImageButton>(R.id.profil_pocetna)

        korisnickaPodrskaButton.setOnClickListener {

            val intent = Intent (this, Profil::class.java)
            startActivity(intent)
        }

    }
}

