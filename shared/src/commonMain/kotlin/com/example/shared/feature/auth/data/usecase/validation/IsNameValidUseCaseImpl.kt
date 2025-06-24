package com.example.shared.feature.auth.data.usecase.validation

import com.example.shared.feature.auth.domain.usecase.validators.IsNameValidUseCase

class IsNameValidUseCaseImpl : IsNameValidUseCase {
    override fun invoke(name: String): Boolean {
        return Regex("^[A-Za-z ]+\$").matches(name)
    }
}