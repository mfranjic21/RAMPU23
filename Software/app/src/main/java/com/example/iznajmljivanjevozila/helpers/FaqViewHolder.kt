package com.example.iznajmljivanjevozila.helpers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.R

class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val naslovPitanja: TextView = itemView.findViewById(R.id.naslovPitanja)
    val odgovorPitanja: TextView = itemView.findViewById(R.id.odgovorPitanja)
}