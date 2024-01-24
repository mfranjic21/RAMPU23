package com.example.iznajmljivanjevozila.helpers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.R


class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val username: TextView = itemView.findViewById(R.id.username)
    val comment: TextView = itemView.findViewById(R.id.comment)
    val grade: TextView = itemView.findViewById(R.id.grade)
}