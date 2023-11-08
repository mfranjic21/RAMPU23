package com.example.iznajmljivanjevozila

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.iznajmljivanjevozila.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val korisnickaPodrska = findViewById<Button>(R.id.korisnickaPodrska)

        korisnickaPodrska.setOnClickListener {
            val intent1 = Intent (this, KorisnickaPodrska::class.java)
            startActivity(intent1)
        }
    }
}