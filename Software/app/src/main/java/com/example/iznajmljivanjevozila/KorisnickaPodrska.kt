package com.example.iznajmljivanjevozila

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val faqButton = findViewById<Button>(R.id.faq)
        val vasaPitanjaButton = findViewById<Button>(R.id.vasaPitanja)
        val posaljiNovoPitanjeButton = findViewById<ImageButton>(R.id.posaljiNovoPitanje)

        fillFAQ()
        fillVasaPitanja()

        natragButton.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }

        faqButton.setOnClickListener {
            faqButton.setBackgroundResource(R.drawable.arrow_border)
            vasaPitanjaButton.setBackgroundResource(android.R.color.transparent)

            val faqPitanja = findViewById<RecyclerView>(R.id.faqPitanja)
            faqPitanja.layoutManager = LinearLayoutManager(this)
            faqPitanja.adapter = FaqAdapter(faqList)
        }

        vasaPitanjaButton.setOnClickListener {
            vasaPitanjaButton.setBackgroundResource(R.drawable.arrow_border)
            faqButton.setBackgroundResource(android.R.color.transparent)

            val faqPitanja = findViewById<RecyclerView>(R.id.faqPitanja)
            faqPitanja.layoutManager = LinearLayoutManager(this)
            faqPitanja.adapter = FaqAdapter(vasaPitanja)
        }

        posaljiNovoPitanjeButton.setOnClickListener {
            val inputPitanjaText = findViewById<EditText>(R.id.inputPitanja)

            vasaPitanjaButton.setBackgroundResource(R.drawable.arrow_border)
            faqButton.setBackgroundResource(android.R.color.transparent)

            if (inputPitanjaText.text.toString() != "") {
                dodajPitanje(inputPitanjaText.text.toString())
                val faqPitanja = findViewById<RecyclerView>(R.id.faqPitanja)
                faqPitanja.layoutManager = LinearLayoutManager(this)
                faqPitanja.adapter = FaqAdapter(vasaPitanja)
            }

            inputPitanjaText.setText("")
        }

        //Temporary Light Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        fillFAQ()

        val faqPitanja = findViewById<RecyclerView>(R.id.faqPitanja)
        faqPitanja.layoutManager = LinearLayoutManager(this)
        faqPitanja.adapter = FaqAdapter(faqList)
    }

    private fun fillFAQ() {
        if (faqList.size == 0) {
            faqList.add(Pitanja("Pitanje #1", "Pitanje #1 glasi...?", "Odgovor na pitanje #1 je..."))
            faqList.add(Pitanja("Pitanje #2", "Pitanje #2 glasi...?", "Odgovor na pitanje #2 je..."))
            faqList.add(Pitanja("Pitanje #3", "Pitanje #3 glasi...?", "Odgovor na pitanje #3 je..."))
            faqList.add(Pitanja("Pitanje #4", "Pitanje #4 glasi...?", "Odgovor na pitanje #4 je..."))
            faqList.add(Pitanja("Pitanje #5", "Pitanje #5 glasi...?", "Odgovor na pitanje #5 je..."))
        }
    }

    private fun fillVasaPitanja() {
        if (vasaPitanja.size == 0) {
            vasaPitanja.add(Pitanja("Vaše pitanje #1", "Pitanje #1 glasi...?", "Odgovor na pitanje #1 je..."))
            vasaPitanja.add(Pitanja("Vaše pitanje #2", "Pitanje #2 glasi...?", "Odgovor na pitanje #2 je..."))
            vasaPitanja.add(Pitanja("Vaše pitanje #3", "Pitanje #3 glasi...?", "Čeka se odgovor administratora."))
        }
    }

    private fun dodajPitanje(pitanje: String) {
        vasaPitanja.add(Pitanja("Vaše Pitanje #${vasaPitanja.size+1}", "${pitanje}", "Čeka se odgovor administratora."))
    }
}




