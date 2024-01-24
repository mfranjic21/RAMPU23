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
import com.example.iznajmljivanjevozila.data.reviewsList

class RecensionFragment: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recension)

        var car = intent.getSerializableExtra("car") as Cars

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var comments = findViewById<RecyclerView>(R.id.otherRecensionsList)
        comments.layoutManager = LinearLayoutManager(this)
        comments.adapter = ReviewListAdapter(reviewsList, car)


        var addRecension = findViewById<ImageButton>(R.id.addRecension)
        addRecension.setOnClickListener{
            var inputRecension = findViewById<EditText>(R.id.inputRecension).text.toString()

            var gradeBar = findViewById<RatingBar>(R.id.gradeBar).rating

            if(inputRecension == ""){
                Toast.makeText(this, "Upišite tekst recenzije", Toast.LENGTH_SHORT).show()
            }else if(gradeBar < 0.5){
                Toast.makeText(this, "Odaberite ocjenu", Toast.LENGTH_SHORT).show()
            }else{
                reviewsList.add(Reviews(car, SessionManager.getLoggedUser(), gradeBar, inputRecension))
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}