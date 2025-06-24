package com.example.shared.feature.auth.data.usecase.validation

import com.example.shared.feature.auth.domain.usecase.validators.IsPasswordValidUseCase

class IsPasswordValidUseCaseImpl : IsPasswordValidUseCase {
    override fun invoke(password: String): Boolean {
        return Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$").matches(password)
    }
}