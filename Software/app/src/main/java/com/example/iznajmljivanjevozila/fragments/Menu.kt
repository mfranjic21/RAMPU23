package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil)

        val uid = Firebase.auth.currentUser!!.uid
        replacePlaceHolder(uid)

        val vracaj = findViewById<ImageButton>(R.id.vrati_nazad)
        vracaj.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val postavkeProfila = findViewById<TextView>(R.id.promijeniProfil)
        postavkeProfila.setOnClickListener {
            val intent = Intent(this, ProfileConfiguration::class.java)
            startActivity(intent)
        }

        val odjava = findViewById<TextView>(R.id.odjava)
        odjava.setOnClickListener {
            var auth = FirebaseAuth.getInstance()
            auth.signOut()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)

        }

        val podrska = findViewById<TextView>(R.id.podrska)
        podrska.setOnClickListener {
            val intent = Intent(this, CustomerService::class.java)
            startActivity(intent)
        }

        val postavkeAplikacije = findViewById<TextView>(R.id.promijeniPostavke)
        postavkeAplikacije.setOnClickListener {
            val intent = Intent(this, AppConfiguration::class.java)
            startActivity(intent)
        }

        val mojeRezervacije = findViewById<TextView>(R.id.mojeRezervacije)
        mojeRezervacije.setOnClickListener {
            val intent = Intent(this, MyReservations::class.java)
            startActivity(intent)
        }

        val kontakt = findViewById<TextView>(R.id.kontakt)
        kontakt.setOnClickListener {
            val intent = Intent(this, Contact::class.java)
            startActivity(intent)
        }

    }

    private fun replacePlaceHolder(uid: String) {
        val database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")
        val activeUser = database.getReference("users")
        val username = findViewById<TextView>(R.id.ime_prezime)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val firstName = dataSnapshot.child("firstname").value.toString()
                    val lastName = dataSnapshot.child("lastname").value.toString()

                    username.text = "$firstName $lastName"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        activeUser.child(uid).addValueEventListener(valueEventListener)
    }



}