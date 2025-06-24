package com.example.shared.feature.auth.data.usecase

import com.example.auth.api.usecase.SignUpUseCase
import com.example.shared.core.database.entity.UserEntity
import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.auth.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SignUpUseCaseImpl(
    private val repository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SignUpUseCase {
    override suspend fun invoke(
        email: String,
        name: String,
        password: String,
    ): Result<UserEntity> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.signUp(email, name, password)
            }
        }
    }
}