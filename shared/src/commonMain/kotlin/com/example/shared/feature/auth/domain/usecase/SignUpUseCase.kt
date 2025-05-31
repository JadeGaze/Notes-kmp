package com.example.auth.api.usecase

import com.example.shared.core.database.entity.UserEntity

interface SignUpUseCase {
    suspend operator fun invoke(
        email: String,
        name: String,
        password: String,
    ): Result<UserEntity>
}