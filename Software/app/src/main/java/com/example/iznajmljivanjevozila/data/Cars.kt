package com.example.iznajmljivanjevozila.data

import com.example.iznajmljivanjevozila.helpers.VehicleDataUpdate
import java.io.Serializable
var carsList = mutableListOf<Cars>()

class Cars(
    var key: String,
    var mark: String,
    var model: String,
    var year: String,
    var registrationPlate: String,
    var currentMileage: String,
    var details: String,
    var availability: Boolean,
    var reservationUser: String,
    var photo: String,
) : Serializable{
    fun toggleAvailability() {
        availability = !availability
    }

    fun reserveCar(username: String) {
        reservationUser = if (reservationUser == ""){
            username
        } else {
            ""
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cars

        return key == other.key && mark == other.mark && model == other.model
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + mark.hashCode()
        result = 31 * result + model.hashCode()
        return result
    }
}