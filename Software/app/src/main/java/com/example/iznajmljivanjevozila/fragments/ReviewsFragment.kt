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
import com.example.iznajmljivanjevozila.data.carsList
import com.example.iznajmljivanjevozila.data.reviewsList

class ReviewsFragment(): AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review)

        var car = intent.getSerializableExtra("car") as Cars
        val reserved = intent.getBooleanExtra("reserved", false)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var selectedCar = findViewById<RecyclerView>(R.id.selectedCar)
        selectedCar.layoutManager = LinearLayoutManager(this)
        selectedCar.adapter = CarListAdapter(carsList, reserved, this, true, car)

        var comments = findViewById<RecyclerView>(R.id.reviewsList)
        comments.layoutManager = LinearLayoutManager(this)
        comments.adapter = ReviewListAdapter(reviewsList, car)
    }

}