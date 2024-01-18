package com.example.iznajmljivanjevozila.Login

object SessionManager {
    private var isLoggedIn: Boolean = false
    private var loggedUser: String = ""

    fun setLoggedIn(isLoggedIn: Boolean) {
        this.isLoggedIn = isLoggedIn
    }

    fun isLoggedIn(): Boolean {
        return isLoggedIn
    }

    fun logginUser(loginUsername: String){
        loggedUser = loginUsername
    }

    fun getLoggedUser(): String{
        return loggedUser
    }
}