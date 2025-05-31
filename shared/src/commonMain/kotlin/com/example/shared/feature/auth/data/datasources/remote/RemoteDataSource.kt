package com.example.shared.feature.auth.data.datasources.remote

import com.example.shared.core.database.entity.UserEntity

interface RemoteDataSource {
    suspend fun signIn(email: String, password: String): UserEntity

    suspend fun signUp(
        name: String,
        password: String,
        email: String,
    ): UserEntity

    suspend fun getCurrentUser(): UserEntity
    suspend fun updateCurrentUser(user: UserEntity)
    suspend fun signOut()
}