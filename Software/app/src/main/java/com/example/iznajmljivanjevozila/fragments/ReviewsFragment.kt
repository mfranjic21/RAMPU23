package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.SessionManager
import com.example.iznajmljivanjevozila.adapters.CarListAdapter
import com.example.iznajmljivanjevozila.adapters.ReviewListAdapter
import com.example.iznajmljivanjevozila.data.Cars
import com.example.iznajmljivanjevozila.data.Reviews
import com.example.iznajmljivanjevozila.data.carsList
import com.example.iznajmljivanjevozila.data.reviewsList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

class ReviewsFragment: AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private var reserved: Boolean? = null
    private lateinit var car: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review)

        database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")

        car = intent.getStringExtra("car") as String
        reserved = intent.getBooleanExtra("reserved", false)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var vehicle = getVehicleData(car!!) as Cars
        var newCarList = mutableListOf<Cars>()
        newCarList.addAll(carsList.filter { it.registrationPlate == vehicle.registrationPlate })

        fillData(reserved!!, newCarList)
    }

    private fun fillData(reserved: Boolean, newCarList: List<Cars>) {
        var selectedCar = findViewById<RecyclerView>(R.id.selectedCar)
        selectedCar.layoutManager = LinearLayoutManager(this)
        selectedCar.adapter = CarListAdapter(carsList, reserved, this, true, newCarList)

        var comments = findViewById<RecyclerView>(R.id.reviewsList)
        comments.layoutManager = LinearLayoutManager(this)
        comments.adapter = ReviewListAdapter(filterReviewList(car))
    }

    private fun filterReviewList(car: String): List<Reviews> {
        var filterList = mutableListOf<Reviews>()
        for (review in reviewsList){
            if (review.car == car){
                filterList.add(review)
            }
        }
        return filterList
    }

    private fun getVehicleData(selectedCar: String) : Cars? {
        for (vehicle in carsList){
            if (vehicle.key == selectedCar) {
                return vehicle
            }
        }
        return null
    }
}