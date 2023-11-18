package com.example.iznajmljivanjevozila

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.iznajmljivanjevozila.data.User
import com.example.iznajmljivanjevozila.helpers.MockDataLoader


class PostavkeProfila : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.postavke_profila)


        val vracaj = findViewById<ImageButton>(R.id.vrati_nazad_promjena)
        vracaj.setOnClickListener {
            val intent = Intent(this, Profil::class.java)
            startActivity(intent)
        }


        val spremi = findViewById<Button>(R.id.spremi)
        spremi.setOnClickListener {
            Toast.makeText(applicationContext,"Uspješno spremljeno",Toast.LENGTH_SHORT).show()
        }

    }

}