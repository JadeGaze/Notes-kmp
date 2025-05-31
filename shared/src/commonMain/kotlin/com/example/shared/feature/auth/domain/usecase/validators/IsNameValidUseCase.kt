package com.example.shared.feature.auth.domain.usecase.validators

interface IsNameValidUseCase {
    operator fun invoke(name: String): Boolean
}