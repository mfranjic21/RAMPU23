package com.example.iznajmljivanjevozila.carsDB

import android.content.Intent
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.Login.SessionManager
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.helpers.MockDataLoader
import com.example.iznajmljivanjevozila.myReservations.MyReservations
import java.util.Collections.addAll

class CarListAdapter(private val carsList: List<Cars>, reserve: Boolean = false) : RecyclerView.Adapter<CarsViewHolder>() {
    private val reserved = reserve
    private var filteredCarsList: List<Cars> = filterCarsList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_card_view, parent, false)
        return CarsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = filteredCarsList[position]

        var grade = "${car.grade}/5.0"
        holder.carPhoto.setImageResource(car.photo)
        holder.carMark.text = car.mark
        holder.carModel.text = car.model
        holder.carYear.text = car.year
        holder.carGrade.text = grade
        holder.carDetails.text = car.details

        val buttonText = if (car.availability) {
            holder.reserveButton.text = holder.itemView.context.getString(R.string.reserve_vehicle)
            holder.reserveButton.isEnabled = true
        } else if (car.reservationUser == loggedUser() && reserved){
            holder.reserveButton.text = holder.itemView.context.getString(R.string.vehicle_reserved)
            holder.reserveButton.isEnabled = true
        } else {
            holder.reserveButton.text = holder.itemView.context.getString(R.string.vehicle_reserved)
            holder.reserveButton.isEnabled = false
        }

        holder.reserveButton.setOnClickListener {
            if (car.availability) {
                car.toggleAvailability()
                car.reserveCar(loggedUser())
                notifyItemChanged(position)
            } else {
                car.toggleAvailability()
                car.reserveCar(loggedUser())
                notifyItemChanged(position)
                filteredCarsList = emptyList()
                filteredCarsList = filterCarsList()
                notifyDataSetChanged()
            }
        }


    }

    fun filterCarsList(): List<Cars> {
        return if (!reserved) {
            carsList
        } else {
            carsList.filter { it.reservationUser == loggedUser() }
        }
    }

    override fun getItemCount(): Int {
        return filteredCarsList.size
    }

    private fun loggedUser(): String{
        return SessionManager.getLoggedUser()
    }
}