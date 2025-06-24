package com.example.impl

import com.example.shared.core.database.entity.UserEntity
import com.example.shared.feature.auth.data.usecase.SignInUseCaseImpl
import com.example.shared.feature.auth.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SignInUseCaseImplTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository: UserRepository = mockk()
    private lateinit var signInUseCase: SignInUseCaseImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        signInUseCase = SignInUseCaseImpl(mockRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `signIn should return success result when repository call is successful`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val expectedUser = UserEntity(
            id = "his",
            name = "Simone Daugherty",
            email = email,
            password = password
        )
        coEvery { mockRepository.signIn(email, password) } returns expectedUser

        // Act
        val result = signInUseCase.invoke(email, password)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expectedUser, result.getOrNull())
    }

    @Test
    fun `signIn should return failure result when repository call fails`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val expectedException = RuntimeException("Network error")
        coEvery { mockRepository.signIn(email, password) } throws expectedException

        // Act
        val result = signInUseCase.invoke(email, password)

        // Assert
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is RuntimeException)
        assertEquals("Network error", exception?.message)
    }
}