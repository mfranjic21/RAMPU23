package com.example.iznajmljivanjevozila.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CursorAdapter
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.iznajmljivanjevozila.SessionManager
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.data.Cars
import com.example.iznajmljivanjevozila.data.Reviews
import com.example.iznajmljivanjevozila.data.reviewsList
import com.example.iznajmljivanjevozila.fragments.CustomerService
import com.example.iznajmljivanjevozila.fragments.Menu
import com.example.iznajmljivanjevozila.fragments.MyReservations
import com.example.iznajmljivanjevozila.fragments.RecensionFragment
import com.example.iznajmljivanjevozila.fragments.ReviewsFragment
import com.example.iznajmljivanjevozila.helpers.CarsViewHolder
import java.text.DecimalFormat


class CarListAdapter(private val carsList: List<Cars>, reserve: Boolean = false, private val context: Context? = null, review: Boolean? = null, car: Cars? = null) : RecyclerView.Adapter<CarsViewHolder>() {
    private val reserved = reserve
    private val review = review ?: false
    private val car = car ?: null
    private var filteredCarsList: List<Cars> = filterCarsList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_card_view, parent, false)
        return CarsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = filteredCarsList[position]

        var gradeSum = 0F
        var gradeCount = 0

        for(review in reviewsList){
            if(review.car.registrationPlate == car.registrationPlate){
                gradeSum += review.grade
                gradeCount ++
            }
        }

        var grade = if (gradeCount != 0){
            val averageGrade = gradeSum / gradeCount
            "${DecimalFormat("#.##").format(averageGrade)}/5.0"
        } else {
            "Još nema recenzija"
        }

        holder.carPhoto.setImageResource(car.photo)
        holder.carMark.text = car.mark
        holder.carModel.text = car.model
        holder.carYear.text = car.year
        holder.carGrade.text = grade
        holder.carDetails.text = car.details

        if (car.availability) {
            holder.reserveButton.text = holder.itemView.context.getString(R.string.reserve_vehicle)
            holder.reserveButton.isEnabled = true
        } else if (car.reservationUser == loggedUser() && reserved){
            holder.reserveButton.text = holder.itemView.context.getString(R.string.remove_reservation)
            holder.reserveButton.isEnabled = true
        } else {
            holder.reserveButton.text = holder.itemView.context.getString(R.string.vehicle_reserved)
            holder.reserveButton.isEnabled = false
        }

        holder.reserveButton.setOnClickListener {
            if (!car.availability && reserved){
                changeAvailability(car, position)
                showPopupRecension(car)
            } else {
                changeAvailability(car, position)
            }
        }

        holder.reviewButton.setOnClickListener {
            val intent = Intent(context, ReviewsFragment::class.java)
            intent.putExtra("car", car)
            intent.putExtra("reserved", reserved)
            context?.startActivity(intent)
        }
    }

    private fun showPopupRecension(returnedCar: Cars) {
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.review_redirection)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val recensionButton = dialog.findViewById<Button>(R.id.recensionButton)
        val closeRecension = dialog.findViewById<Button>(R.id.closeRecension)

        closeRecension.setOnClickListener {
            dialog.dismiss()
        }

        recensionButton.setOnClickListener {
            val intent = Intent(context, RecensionFragment::class.java)
            intent.putExtra("car", returnedCar)
            context?.startActivity(intent)
        }


        dialog.setOnDismissListener {
            filteredCarsList = emptyList()
            filteredCarsList = filterCarsList()

            notifyDataSetChanged()

            if (filteredCarsList.isEmpty()){
                val intent = Intent (context, MainActivity::class.java)
                context?.startActivity(intent)
            }
        }

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    private fun changeAvailability(car: Cars, position: Int) {
        car.toggleAvailability()
        car.reserveCar(loggedUser())
        notifyItemChanged(position)
    }

    fun filterCarsList(): List<Cars> {
        return if (review) {
            var selectedCar = car as Cars
            carsList.filter { it.registrationPlate == selectedCar.registrationPlate }
        } else if (!reserved){
            carsList
        } else {
            carsList.filter { it.reservationUser == loggedUser() }
        }
    }

    override fun getItemCount(): Int {
        return filteredCarsList.size
    }

    private fun loggedUser(): String {
        return SessionManager.getLoggedUser().username
    }
}