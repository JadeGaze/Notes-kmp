package com.example.shared.feature.auth.ui

interface SignUpViewModel {
    fun signUp(email: String, name: String, password: String)

    fun validate(email: String, name: String, password: String): Boolean

    fun validateEmail(email: String)

    fun validateName(name: String)

    fun validatePassword(password: String)
}