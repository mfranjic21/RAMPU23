package com.example.iznajmljivanjevozila.myReservations

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.Profil
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.carsDB.CarListAdapter
import com.example.iznajmljivanjevozila.carsDB.carsList


class MyReservations : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_reservations)

        val backButton = findViewById<ImageButton>(R.id.btnBack)
        val txtListEmpty = findViewById<TextView>(R.id.noReservation)

        backButton.setOnClickListener {
            val intent = Intent (this, Profil::class.java)
            startActivity(intent)
        }

        val filteredCarsList = CarListAdapter(carsList, true).filterCarsList()

        if (filteredCarsList.isEmpty()){
            txtListEmpty.text = getString(R.string.listEmpty)
        } else {
            txtListEmpty.text = ""
        }

        val myReservations = findViewById<RecyclerView>(R.id.myReservationsView)
        myReservations.layoutManager = LinearLayoutManager(this)
        myReservations.adapter = CarListAdapter(carsList, true)
    }
}



