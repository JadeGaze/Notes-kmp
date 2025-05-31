package com.example.shared.feature.auth.data.datasources.local

import com.example.shared.core.database.entity.UserEntity


interface LocalDataSource {
    suspend fun signIn(user: UserEntity)
    suspend fun getCurrentUser(): UserEntity
    suspend fun updateCurrentUser(user: UserEntity)
    suspend fun signOut()
    suspend fun isAuth(): String?
}