package com.example.auth.api.usecase.validators

interface IsNotEmptyStringUseCase {
    operator fun invoke(string: String): Boolean
}