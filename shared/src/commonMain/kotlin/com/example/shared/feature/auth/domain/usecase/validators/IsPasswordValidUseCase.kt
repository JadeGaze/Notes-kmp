package com.example.shared.feature.auth.domain.usecase.validators

interface IsPasswordValidUseCase {
    operator fun invoke(password: String): Boolean
}