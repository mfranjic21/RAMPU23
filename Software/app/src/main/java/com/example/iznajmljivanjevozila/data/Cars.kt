package com.example.iznajmljivanjevozila.data

var carsList = mutableListOf<Cars>()

class Cars(
    var mark: String,
    var model: String,
    var year: String,
    var grade: Double,
    var registrationPlate: String,
    var currentMileage: String,
    var details: String,
    var availability: Boolean,
    var reservationUser: String,
    var photo: Int,
) {
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

    fun available(): Boolean {
        return availability
    }
}