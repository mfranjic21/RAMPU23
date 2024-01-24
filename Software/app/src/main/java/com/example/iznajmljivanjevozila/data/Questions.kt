package com.example.iznajmljivanjevozila.data

val faqList = mutableListOf<Questions>()
val yourQuestions = mutableListOf<Questions>()

data class Questions (
    var question: String,
    var answer: String
)


