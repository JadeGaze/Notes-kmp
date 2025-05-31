package com.example.shared.feature.auth.data.usecase

import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.auth.domain.repository.UserRepository
import com.example.shared.feature.auth.domain.usecase.IsAuthUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class IsAuthUseCaseImpl(
    private val repository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : IsAuthUseCase {
    override suspend fun invoke(): Result<String?> {
        return runSuspendCatching { withContext(dispatcher) { (repository.isAuth()) } }
    }
}