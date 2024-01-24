package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.adapters.CarListAdapter
import com.example.iznajmljivanjevozila.data.carsList


class MyReservations : AppCompatActivity(){

    private lateinit var txtListEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_reservations)

        val backButton = findViewById<ImageButton>(R.id.btnBack)
        txtListEmpty = findViewById(R.id.noReservation)

        backButton.setOnClickListener {
            val intent = Intent (this, Menu::class.java)
            startActivity(intent)
        }

        changeLabelEmptyList()

        val myReservations = findViewById<RecyclerView>(R.id.myReservationsView)
        myReservations.layoutManager = LinearLayoutManager(this)
        myReservations.adapter = CarListAdapter(carsList, true, this)
    }

    fun changeLabelEmptyList(){
        val filteredCarsList = CarListAdapter(carsList, true, this).filterCarsList()

        txtListEmpty = findViewById(R.id.noReservation)

        if (filteredCarsList.isEmpty()){
            txtListEmpty.text = getString(R.string.listEmpty)
        } else {
            txtListEmpty.text = ""
        }
    }
}

