package com.example.shared.feature.auth.domain.usecase

interface IsAuthUseCase {
    suspend operator fun invoke(): Result<String?>
}