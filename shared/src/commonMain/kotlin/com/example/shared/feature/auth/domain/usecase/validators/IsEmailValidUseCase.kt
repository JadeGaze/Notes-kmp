package com.example.auth.api.usecase.validators

interface IsEmailValidUseCase {
    operator fun invoke(email: String): Boolean
}