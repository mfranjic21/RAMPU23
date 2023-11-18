package com.example.iznajmljivanjevozila

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PostavkeProfila : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.postavke_profila)


        val vracaj = findViewById<ImageButton>(R.id.vrati_nazad_promjena)
        vracaj.setOnClickListener {
            val intent = Intent(this, Profil::class.java)
            startActivity(intent)
        }

    }
}