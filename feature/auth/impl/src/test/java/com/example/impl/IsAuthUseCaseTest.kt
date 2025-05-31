package com.example.impl

import com.example.shared.feature.auth.data.usecase.IsAuthUseCaseImpl
import com.example.shared.feature.auth.domain.repository.UserRepository
import com.example.shared.feature.auth.domain.usecase.IsAuthUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IsAuthUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository: UserRepository = mockk()
    private lateinit var isAuthUseCase: IsAuthUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        isAuthUseCase = IsAuthUseCaseImpl(mockRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `isAuth should return true when repository call is successful`() = runTest {
        // Arrange
        val expectedResult = "user id"
        coEvery { mockRepository.isAuth() } returns expectedResult

        // Act
        val result = isAuthUseCase.invoke()

        // Assert
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(expectedResult, result.getOrNull())
    }

    @Test
    fun `isAuth should return failure result when repository call fails`() = runTest {
        // Arrange
        val expectedException = RuntimeException("Something went wrong")
        coEvery { mockRepository.isAuth() } throws expectedException

        // Act
        val result = isAuthUseCase.invoke()

        // Assert
        Assert.assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        Assert.assertTrue(exception is RuntimeException)
        Assert.assertEquals("Something went wrong", exception?.message)
    }

    @Test
    fun `isAuth should return false when repository call is successful`() = runTest {
        // Arrange
        coEvery { mockRepository.isAuth() } returns null

        // Act
        val result = isAuthUseCase.invoke()

        // Assert
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(null, result.getOrNull())
    }

}