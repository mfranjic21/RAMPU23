package com.example.iznajmljivanjevozila.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.SessionManager
import com.example.iznajmljivanjevozila.adapters.ReviewListAdapter
import com.example.iznajmljivanjevozila.data.Cars
import com.example.iznajmljivanjevozila.data.Reviews
import com.example.iznajmljivanjevozila.data.carsList
import com.example.iznajmljivanjevozila.data.reviewsList
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database

class RecensionFragment: AppCompatActivity() {

    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recension)

        database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")
        var car = intent.getStringExtra("car") as String
        var uid = Firebase.auth.currentUser!!.uid

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var comments = findViewById<RecyclerView>(R.id.otherRecensionsList)
        comments.layoutManager = LinearLayoutManager(this)
        comments.adapter = ReviewListAdapter(filterReviewList(car))


        var addRecension = findViewById<ImageButton>(R.id.addRecension)
        addRecension.setOnClickListener{
            var inputRecension = findViewById<EditText>(R.id.inputRecension).text.toString()

            var gradeBar = findViewById<RatingBar>(R.id.gradeBar).rating

            if(inputRecension == ""){
                Toast.makeText(this, "Upišite tekst recenzije", Toast.LENGTH_SHORT).show()
            }else if(gradeBar < 0.5){
                Toast.makeText(this, "Odaberite ocjenu", Toast.LENGTH_SHORT).show()
            }else{
                reviewsList.add(Reviews(car, uid, gradeBar, inputRecension))
                addNewRecension(car, uid, gradeBar, inputRecension)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
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

    private fun addNewRecension(car: String, uid: String, gradeBar: Float, inputRecension: String) {
        val reviewsRef = database.getReference("reviews")

        val newReviewsKey = reviewsRef.push().key

        if (newReviewsKey != null) {
            val reviewData = HashMap<String, Any>()
            reviewData["vehicle"] = car
            reviewData["user"] = uid
            reviewData["grade"] = gradeBar
            reviewData["comment"] = inputRecension

            reviewsRef.child(newReviewsKey).setValue(reviewData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                    } else {
                    }
                }
        }
    }
}