package com.example.shared.feature.auth.domain.usecase

interface SignOutUseCase {
    suspend operator fun invoke(): Result<Unit>
}