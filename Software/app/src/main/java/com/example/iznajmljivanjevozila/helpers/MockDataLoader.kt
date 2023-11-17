package com.example.iznajmljivanjevozila.helpers

import com.example.iznajmljivanjevozila.data.User

object MockDataLoader {
    fun getUsers(): List<User> = listOf(
        User("test", "test")
    )
}