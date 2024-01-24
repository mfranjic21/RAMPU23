package com.example.iznajmljivanjevozila

import com.example.iznajmljivanjevozila.data.User

object SessionManager {
    private var isLoggedIn: Boolean = false
    private lateinit var loggedUser: User

    fun setLoggedIn(isLoggedIn: Boolean) {
        SessionManager.isLoggedIn = isLoggedIn
    }

    fun isLoggedIn(): Boolean {
        return isLoggedIn
    }

    fun logginUser(user: User){
        loggedUser = user
    }

    fun getLoggedUser(): User {
        return loggedUser
    }
}