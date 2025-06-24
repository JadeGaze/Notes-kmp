package com.example.shared.feature.auth.domain.usecase

import com.example.shared.core.database.entity.UserEntity

interface SignInUseCase {
    suspend operator fun invoke(email: String, password: String): Result<UserEntity>
}