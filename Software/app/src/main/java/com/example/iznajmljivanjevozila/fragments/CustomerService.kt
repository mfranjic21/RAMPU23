package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.FaqAdapter
import com.example.iznajmljivanjevozila.data.Questions
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.data.faqList
import com.example.iznajmljivanjevozila.data.yourQuestions


class CustomerService : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_service)

        val natragButton = findViewById<ImageButton>(R.id.natrag)
        val faqButton = findViewById<Button>(R.id.faq)
        val vasaPitanjaButton = findViewById<Button>(R.id.vasaPitanja)
        val posaljiNovoPitanjeButton = findViewById<ImageButton>(R.id.posaljiNovoPitanje)

        fillFAQ()
        fillVasaPitanja()

        natragButton.setOnClickListener {
            val intent = Intent (this, Menu::class.java)
            startActivity(intent)
        }

        faqButton.setOnClickListener {
            faqButton.setBackgroundResource(R.drawable.arrow_border)
            vasaPitanjaButton.setBackgroundResource(android.R.color.transparent)

            val faqPitanja = findViewById<RecyclerView>(R.id.faqQuestions)
            faqPitanja.layoutManager = LinearLayoutManager(this)
            faqPitanja.adapter = FaqAdapter(faqList)
        }

        vasaPitanjaButton.setOnClickListener {
            vasaPitanjaButton.setBackgroundResource(R.drawable.arrow_border)
            faqButton.setBackgroundResource(android.R.color.transparent)

            val faqPitanja = findViewById<RecyclerView>(R.id.faqQuestions)
            faqPitanja.layoutManager = LinearLayoutManager(this)
            faqPitanja.adapter = FaqAdapter(yourQuestions)
        }

        posaljiNovoPitanjeButton.setOnClickListener {
            val inputPitanjaText = findViewById<EditText>(R.id.inputPitanja)

            vasaPitanjaButton.setBackgroundResource(R.drawable.arrow_border)
            faqButton.setBackgroundResource(android.R.color.transparent)

            if (inputPitanjaText.text.toString() != "") {
                dodajPitanje(inputPitanjaText.text.toString())
                val faqPitanja = findViewById<RecyclerView>(R.id.faqQuestions)
                faqPitanja.layoutManager = LinearLayoutManager(this)
                faqPitanja.adapter = FaqAdapter(yourQuestions)
            }

            inputPitanjaText.setText("")
        }

        fillFAQ()


        val faqQuestions = findViewById<RecyclerView>(R.id.faqQuestions)
        faqQuestions.layoutManager = LinearLayoutManager(this)
        faqQuestions.adapter = FaqAdapter(faqList)
    }

    private fun fillFAQ() {
        if (faqList.size == 0) {
            for (i in 1..8) {
                faqList.add(Questions("Pitanje #${i}", "Odgovor na pitanje #${i} je..."))
            }
        }
    }

    private fun fillVasaPitanja() {
        if (yourQuestions.size == 0) {
            yourQuestions.add(Questions("Vaše pitanje #1", "Odgovor na pitanje #1 je..."))
            yourQuestions.add(Questions("Vaše pitanje #2", "Odgovor na pitanje #2 je..."))
            yourQuestions.add(Questions("Vaše pitanje #3", "Čeka se odgovor administratora."))
        }
    }

    private fun dodajPitanje(pitanje: String) {
        yourQuestions.add(Questions(pitanje, "Čeka se odgovor administratora."))
    }
}




