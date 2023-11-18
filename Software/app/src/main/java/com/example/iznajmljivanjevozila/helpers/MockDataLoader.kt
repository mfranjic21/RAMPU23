package com.example.iznajmljivanjevozila.helpers

import com.example.iznajmljivanjevozila.data.User

object MockDataLoader {
    fun getUsers(): List<User> =  UsersList.users.toList()

    fun existsEmail(email:String): Boolean{
        val users = UsersList.users
        return users.any{user -> user.email == email}
    }

    fun existsUsername(username:String): Boolean{
        val users = UsersList.users
        return users.any{user -> user.username == username}
    }

    fun addUser(user : User){
        val users = UsersList.users
        users.add(user)
    }
}

object UsersList{
    var users: MutableList<User> = mutableListOf(
        User("test_ime","test_prezime", "test@example.com", "test" , "test")
    )
}