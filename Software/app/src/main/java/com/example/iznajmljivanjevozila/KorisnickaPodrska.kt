package com.example.iznajmljivanjevozila

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class KorisnickaPodrska : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.korisnicka_podrska)

        val natragButton = findViewById<ImageButton>(R.id.natrag)

        natragButton.setOnClickListener {

            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }

        //Temporary Light Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        fillFAQ()

        val faqPitanja = findViewById<RecyclerView>(R.id.faqPitanja)
        faqPitanja.layoutManager = LinearLayoutManager(this)
        //val limitedList = faqList.take(5)
        faqPitanja.adapter = FaqAdapter(faqList)
    }

    private fun fillFAQ() {
        faqList.clear()
        faqList.add(Pitanja("Pitanje #1", "Pitanje #1 glasi...?", "Odgovor na pitanje #1 je..."))
        faqList.add(Pitanja("Pitanje #2", "Pitanje #2 glasi...?", "Odgovor na pitanje #2 je..."))
        faqList.add(Pitanja("Pitanje #3", "Pitanje #3 glasi...?", "Odgovor na pitanje #3 je..."))
        faqList.add(Pitanja("Pitanje #4", "Pitanje #4 glasi...?", "Odgovor na pitanje #4 je..."))
        faqList.add(Pitanja("Pitanje #5", "Pitanje #5 glasi...?", "Odgovor na pitanje #5 je..."))
    }
}