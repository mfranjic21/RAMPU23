package com.example.iznajmljivanjevozila.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.data.Cars
import com.example.iznajmljivanjevozila.data.Reviews
import com.example.iznajmljivanjevozila.helpers.FaqViewHolder
import com.example.iznajmljivanjevozila.helpers.ReviewsViewHolder

class ReviewListAdapter(private val reviewList: List<Reviews>, private val selectedCar: Cars) : RecyclerView.Adapter<ReviewsViewHolder>() {

    private var filteredReviewsList: List<Reviews> = filterReviewsList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_card_view, parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val reviews = filteredReviewsList[position]

        holder.username.text = reviews.user.firstname+" "+reviews.user.lastname
        holder.comment.text = reviews.comment
        holder.grade.text = reviews.grade.toString()
    }

    private fun filterReviewsList(): List<Reviews> {
        return reviewList.filter { it.car.registrationPlate == selectedCar.registrationPlate }
    }

    override fun getItemCount(): Int {
        return filteredReviewsList.size
    }
}