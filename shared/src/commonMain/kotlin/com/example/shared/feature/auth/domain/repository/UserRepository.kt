package com.example.shared.feature.auth.domain.repository

import com.example.shared.core.database.entity.UserEntity

interface UserRepository {
    suspend fun signUp(
        email: String,
        name: String,
        password: String,
    ): UserEntity

    suspend fun signIn(email: String, password: String): UserEntity
    suspend fun getCurrentUser(): UserEntity
    suspend fun updateUser(user: UserEntity)
    suspend fun signOut()
    suspend fun isAuth(): String?

}