package com.example.iznajmljivanjevozila.helpers

import com.example.iznajmljivanjevozila.data.User

object MockDataLoader {
    fun getUsers(): List<User> =  UsersList.users.toList()
}

object UsersList{
    var users: List<User> = mutableListOf(
        User("test_ime","test_prezime", "test@example.com", "test" , "test")
    )
}