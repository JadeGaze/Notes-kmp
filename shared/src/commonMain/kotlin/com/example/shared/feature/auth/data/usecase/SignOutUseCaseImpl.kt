package com.example.shared.feature.auth.data.usecase

import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.auth.domain.repository.UserRepository
import com.example.shared.feature.auth.domain.usecase.SignOutUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SignOutUseCaseImpl(
    private val repository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SignOutUseCase {
    override suspend fun invoke(): Result<Unit> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.signOut()
            }
        }
    }
}