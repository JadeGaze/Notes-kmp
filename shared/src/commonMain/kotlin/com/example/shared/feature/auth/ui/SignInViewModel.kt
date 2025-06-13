package com.example.shared.feature.auth.ui

interface SignInViewModel {
    fun signIn(email: String, password: String)
    fun validate(email: String, password: String): Boolean
    fun validateEmail(email: String)
    fun validatePassword(password: String)
    fun isAuth()
}