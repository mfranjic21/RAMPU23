package com.example.iznajmljivanjevozila.data

var reviewsList = mutableListOf<Reviews>()

class Reviews (
    var car: Cars,
    var user: User,
    var grade: Float,
    var comment: String
)