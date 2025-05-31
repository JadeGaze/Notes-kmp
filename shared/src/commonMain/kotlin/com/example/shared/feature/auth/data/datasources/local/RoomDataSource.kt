package com.example.shared.feature.auth.data.datasources.local

import com.example.shared.core.database.dao.UserDao
import com.example.shared.core.database.entity.UserEntity

class RoomDataSource(
    private val userDao: UserDao,
) : LocalDataSource {
    override suspend fun signIn(user: UserEntity) {
        userDao.createUser(user)
    }

    override suspend fun getCurrentUser(): UserEntity {
        return userDao.getCurrentUser()!!
    }

    override suspend fun updateCurrentUser(user: UserEntity) {
        userDao.updateCurrentUser(user)
    }

    override suspend fun signOut() {
        userDao.deleteUser()
    }

    override suspend fun isAuth(): String? {
        return userDao.getCurrentUser()?.id
    }
}