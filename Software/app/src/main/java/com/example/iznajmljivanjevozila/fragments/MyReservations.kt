package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.adapters.CarListAdapter
import com.example.iznajmljivanjevozila.data.Cars
import com.example.iznajmljivanjevozila.data.Reviews
import com.example.iznajmljivanjevozila.data.carsList
import com.example.iznajmljivanjevozila.data.reviewsList
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MyReservations : AppCompatActivity(){

    private lateinit var txtListEmpty: TextView
    lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_reservations)

        val backButton = findViewById<ImageButton>(R.id.btnBack)
        uid = Firebase.auth.currentUser!!.uid
        txtListEmpty = findViewById(R.id.noReservation)

        backButton.setOnClickListener {
            val intent = Intent (this, Menu::class.java)
            startActivity(intent)
        }

        changeLabelEmptyList()


        val myReservations = findViewById<RecyclerView>(R.id.myReservationsView)
        myReservations.layoutManager = LinearLayoutManager(this)
        myReservations.adapter = CarListAdapter(filterCars(), true, this)
    }

    fun changeLabelEmptyList(){
        val filteredCarsList = carsList.filter { it.reservationUser == uid }

        txtListEmpty = findViewById(R.id.noReservation)

        if (filteredCarsList.isEmpty()){
            txtListEmpty.text = getString(R.string.listEmpty)
        } else {
            txtListEmpty.text = ""
        }
    }

    fun filterCars() : List<Cars>{
        var filterList = mutableListOf<Cars>()
        for (car in carsList){
            if (car.reservationUser == uid){
                filterList.add(car)
            }
        }
        return filterList
    }
}

