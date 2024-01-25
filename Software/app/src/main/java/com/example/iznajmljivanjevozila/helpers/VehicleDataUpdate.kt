package com.example.iznajmljivanjevozila.helpers

import android.content.Context
import android.widget.Toast
import com.example.iznajmljivanjevozila.data.Cars
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import kotlinx.coroutines.tasks.await

class VehicleDataUpdate {

    private var database = com.google.firebase.ktx.Firebase.database("https://iznajmljivanje-vozila-default-rtdb.europe-west1.firebasedatabase.app/")

    suspend fun updateVehicleData(car: Cars) {
        val vehicleRef: DatabaseReference = database.getReference("vehicles").child(car.key)

        val updateMap = HashMap<String, Any>()
        updateMap["mark"] = car.mark
        updateMap["model"] = car.model
        updateMap["year"] = car.year
        updateMap["registrationPlate"] = car.registrationPlate
        updateMap["currentMileage"] = car.currentMileage
        updateMap["details"] = car.details
        updateMap["availability"] = car.availability
        updateMap["reservationUser"] = car.reservationUser
        updateMap["photo"] = car.photo

        vehicleRef.updateChildren(updateMap).await()
    }
}
