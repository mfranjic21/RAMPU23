package com.example.iznajmljivanjevozila.Login

object SessionManager {
    private var isLoggedIn: Boolean = false

    fun setLoggedIn(isLoggedIn: Boolean) {
        this.isLoggedIn = isLoggedIn
    }

    fun isLoggedIn(): Boolean {
        return isLoggedIn
    }
}