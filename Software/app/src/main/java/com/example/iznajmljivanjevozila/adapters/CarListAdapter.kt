package com.example.iznajmljivanjevozila.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iznajmljivanjevozila.MainActivity
import com.example.iznajmljivanjevozila.R
import com.example.iznajmljivanjevozila.data.Cars
import com.example.iznajmljivanjevozila.data.Notification
import com.example.iznajmljivanjevozila.data.reviewsList
import com.example.iznajmljivanjevozila.fragments.RecensionFragment
import com.example.iznajmljivanjevozila.fragments.ReviewsFragment
import com.example.iznajmljivanjevozila.helpers.CarsViewHolder
import com.example.iznajmljivanjevozila.helpers.NotificationService
import com.example.iznajmljivanjevozila.helpers.VehicleDataUpdate
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.text.DecimalFormat



class CarListAdapter(private val carsList: List<Cars>, private val notificationList: List<Notification>, reserve: Boolean = false, private val context: Context? = null, review: Boolean? = null, newCarList: List<Cars>? = null) : RecyclerView.Adapter<CarsViewHolder>() {
    private val reserved = reserve
    private val review = review ?: false
    private val newCarList = newCarList ?: null
    private var uid = checkIfLogged()

    fun checkIfLogged(): String{
        if(Firebase.auth.currentUser != null){
            return Firebase.auth.currentUser?.uid  as String
        }else{
            return ""
        }
    }

    private var filteredCarsList: List<Cars> = filterCarsList(uid)
    private var database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_card_view, parent, false)
        return CarsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val car = filteredCarsList[position]

        var gradeSum = 0F
        var gradeCount = 0

        for(review in reviewsList){
            if(review.car == car.key){
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

        loadPhoto(car.photo, holder.carPhoto)
        holder.carMark.text = car.mark
        holder.carModel.text = car.model
        holder.carYear.text = car.year
        holder.carGrade.text = grade
        holder.carDetails.text = car.details

        val notification = notificationList.find { n -> n.vehicle == car.key }
        if(notification != null){
            holder.notificationStatus = true
            holder.notificationButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.notification_bell, 0, 0, 0)
        }else{
            holder.notificationStatus = false
            holder.notificationButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.notification_bell_off, 0, 0, 0)
        }



        if (car.availability) {
            holder.reserveButton.text = holder.itemView.context.getString(R.string.reserve_vehicle)
            holder.reserveButton.isEnabled = true
        } else if (car.reservationUser == uid && reserved){
            holder.reserveButton.text = holder.itemView.context.getString(R.string.remove_reservation)
            holder.reserveButton.isEnabled = true
        } else {
            holder.reserveButton.text = holder.itemView.context.getString(R.string.vehicle_reserved)
            holder.reserveButton.isEnabled = false
        }

        if(car.reservationUser != uid){
            if( holder.reserveButton.isEnabled){
                holder.notificationButton.visibility = View.GONE
            }else{
                holder.notificationButton.visibility = View.VISIBLE
            }
        }else{
            holder.notificationButton.visibility = View.GONE
        }

        holder.reserveButton.setOnClickListener {
            if (!car.availability && reserved){
                changeAvailability(car, position, uid)
                showPopupRecension(car)
            } else {
                changeAvailability(car, position, uid)
            }
        }

        holder.reviewButton.setOnClickListener {
            startReviewsFragment(car.key)
        }

        holder.notificationButton.setOnClickListener{
            val notificationsHelper = NotificationService()
            if(holder.notificationStatus){
                holder.notificationStatus = false
                holder.notificationButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.notification_bell_off, 0, 0, 0)
                notificationsHelper.updateNotification(uid, car.key, "remove")
            }else{
                holder.notificationStatus = true
                holder.notificationButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.notification_bell, 0, 0, 0)
                notificationsHelper.updateNotification(uid, car.key, "add")
            }
        }
    }


    private fun loadPhoto(photoUrl: String, imageView: ImageView) {
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(photoUrl)

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(context!!)
                .load(uri)
                .into(imageView)
        }
    }

    private fun startReviewsFragment(key: String){
        val intent = Intent(context, ReviewsFragment::class.java)
        intent.putExtra("reserved", reserved)
        intent.putExtra("car", key)
        context?.startActivity(intent)
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
            intent.putExtra("car", returnedCar.key)
            context?.startActivity(intent)
        }


        dialog.setOnDismissListener {
            filteredCarsList = emptyList()
            filteredCarsList = filterCarsList(uid)

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

    private fun changeAvailability(car: Cars, position: Int, uid: String) {
        for (vehicle in carsList){
            if (vehicle.key == car.key){
                vehicle.toggleAvailability()
                vehicle.reserveCar(uid)
            }
        }

        notifyItemChanged(position)

        updateData(car)
    }

    fun updateData(car: Cars) = GlobalScope.async {
        val vehicleDataUpdate = VehicleDataUpdate()
        vehicleDataUpdate.updateVehicleData(car)
    }

    private fun filterCarsList(uid: String): List<Cars> {
        var filterList = removeDuplicates(carsList)
        return if (review && newCarList!!.isNotEmpty()) {
            removeDuplicates(newCarList)
        } else if (!reserved) {
            filterList
        } else {
            filterList.filter { it.reservationUser == uid }
        }
    }

    fun <Cars> removeDuplicates(inputList: List<Cars>): List<Cars> {
        val set = LinkedHashSet(inputList)
        return ArrayList(set)
    }


    override fun getItemCount(): Int {
        Log.d("Error","${filteredCarsList.size}")
        return filteredCarsList.size
    }

}