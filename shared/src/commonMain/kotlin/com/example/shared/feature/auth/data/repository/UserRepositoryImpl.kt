package com.example.shared.feature.auth.data.repository

import com.example.shared.core.database.entity.UserEntity
import com.example.shared.feature.auth.data.datasources.local.LocalDataSource
import com.example.shared.feature.auth.data.datasources.remote.RemoteDataSource
import com.example.shared.feature.auth.domain.repository.UserRepository


class UserRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
//    private val preferencesDataSource: PreferencesDataSource,
) : UserRepository {
    override suspend fun signUp(
        email: String,
        name: String,
        password: String,
    ): UserEntity {
        val user = remoteDataSource.signUp(name, password, email)
        localDataSource.signIn(user)
//        preferencesDataSource.signIn(email)
        return user
    }

    override suspend fun signIn(email: String, password: String): UserEntity {
        val user = remoteDataSource.signIn(email, password)
        localDataSource.signIn(user)
//        preferencesDataSource.signIn("${user.id}")
        return user
    }

    override suspend fun getCurrentUser(): UserEntity {
        return localDataSource.getCurrentUser()
        return UserEntity(
            id = TODO(),
            name = TODO(),
            email = TODO(),
            password = TODO()
        )
    }

    override suspend fun updateUser(user: UserEntity) {
        localDataSource.updateCurrentUser(user)
        remoteDataSource.updateCurrentUser(user)
    }

    override suspend fun signOut() {
        localDataSource.signOut()
        remoteDataSource.signOut()
//        preferencesDataSource.signOut()
    }

    override suspend fun isAuth(): String? {
        return localDataSource.isAuth()
    }
}