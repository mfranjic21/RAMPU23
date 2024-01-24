package com.example.iznajmljivanjevozila.helpers

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.R

class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val carPhoto: ImageView = itemView.findViewById(R.id.phtCarPhoto)
    val carMark: TextView = itemView.findViewById(R.id.txtCarMark)
    val carModel: TextView = itemView.findViewById(R.id.txtCarModel)
    val carYear: TextView = itemView.findViewById(R.id.txtCarYear)
    val carGrade: TextView = itemView.findViewById(R.id.txtCarGrade)
    val carDetails: TextView = itemView.findViewById(R.id.txtCarDetails)
    val reserveButton: Button = itemView.findViewById(R.id.reserveButton)
    val reviewButton: Button = itemView.findViewById(R.id.reviewButton)!!
}