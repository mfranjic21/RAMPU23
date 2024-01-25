package com.example.iznajmljivanjevozila.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.data.Cars
import com.example.iznajmljivanjevozila.data.Reviews
import com.example.iznajmljivanjevozila.data.User
import com.example.iznajmljivanjevozila.data.userList
import com.example.iznajmljivanjevozila.fragments.ReviewsFragment
import com.example.iznajmljivanjevozila.helpers.FaqViewHolder
import com.example.iznajmljivanjevozila.helpers.ReviewsViewHolder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

class ReviewListAdapter(private val reviewList: List<Reviews>) : RecyclerView.Adapter<ReviewsViewHolder>() {

    private var reviewsList: List<Reviews> = reviewList
    private var database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_card_view, parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val reviews = reviewsList[position]

        for(user in userList){
            if (user.username == reviews.user){
                holder.username.text = user.firstname+" "+user.lastname
            }
        }
        holder.comment.text = reviews.comment
        holder.grade.text = reviews.grade.toString()

    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }
}