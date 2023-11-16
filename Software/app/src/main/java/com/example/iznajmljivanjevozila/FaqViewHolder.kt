package com.example.iznajmljivanjevozila

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val brojPitanja: TextView = itemView.findViewById(R.id.brojPitanja)
    val naslovPitanja: TextView = itemView.findViewById(R.id.naslovPitanja)
    val odgovorPitanja: TextView = itemView.findViewById(R.id.odgovorPitanja)
}