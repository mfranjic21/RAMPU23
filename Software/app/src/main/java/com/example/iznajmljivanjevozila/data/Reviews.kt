package com.example.iznajmljivanjevozila.data

var reviewsList = mutableListOf<Reviews>()

class Reviews (
    var car: String,
    var user: String,
    var grade: Float,
    var comment: String
)