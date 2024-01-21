package com.example.iznajmljivanjevozila.data

import android.provider.ContactsContract
import com.example.iznajmljivanjevozila.Questions

data class User(
    val firstname: String,
    val lastname: String,
    val email: String,
    val username: String,
    val password: String
)
