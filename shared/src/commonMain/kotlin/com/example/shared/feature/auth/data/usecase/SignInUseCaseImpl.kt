package com.example.shared.feature.auth.data.usecase

import com.example.shared.core.database.entity.UserEntity
import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.auth.domain.repository.UserRepository
import com.example.shared.feature.auth.domain.usecase.SignInUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SignInUseCaseImpl(
    private val repository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SignInUseCase {
    override suspend fun invoke(email: String, password: String): Result<UserEntity> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.signIn(email, password)
            }
        }
    }
}