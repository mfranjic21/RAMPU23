package com.example.iznajmljivanjevozila

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.iznajmljivanjevozila.R

class KorisnickaPodrska : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.korisnicka_podrska)

        findViewById<Button>(R.id.natrag).setOnClickListener {
            startActivity(Intent (this, MainActivity::class.java))
        }
    }
}