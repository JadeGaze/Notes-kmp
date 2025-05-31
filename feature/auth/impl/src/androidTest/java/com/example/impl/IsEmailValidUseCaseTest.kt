package com.example.impl

import com.example.shared.feature.auth.data.usecase.validation.IsEmailValidUseCaseImpl
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IsEmailValidUseCaseTest {

    private val isEmailValidUseCase = IsEmailValidUseCaseImpl()

    @Test
    fun validEmail() {
        // Arrange
        val validEmail = "test@example.com"

        // Act
        val result = isEmailValidUseCase.invoke(validEmail)

        // Assert
        assertTrue(result)
    }

    @Test
    fun invalidEmailWithoutDomain() {
        // Arrange
        val invalidEmail = "test@"

        // Act
        val result = isEmailValidUseCase.invoke(invalidEmail)

        // Assert
        assertFalse(result)
    }

    @Test
    fun invalidEmailWithoutAtSymbol() {
        // Arrange
        val invalidEmail = "testexample.com"

        // Act
        val result = isEmailValidUseCase.invoke(invalidEmail)

        // Assert
        assertFalse(result)
    }

    @Test
    fun invalidEmptyEmail() {
        // Arrange
        val invalidEmail = ""

        // Act
        val result = isEmailValidUseCase.invoke(invalidEmail)

        // Assert
        assertFalse(result)
    }

    @Test
    fun invalidEmailWithSpaces() {
        // Arrange
        val invalidEmail = "test @example.com"

        // Act
        val result = isEmailValidUseCase.invoke(invalidEmail)

        // Assert
        assertFalse(result)
    }

    @Test
    fun invalidEmailWithInvalidCharacters() {
        // Arrange
        val invalidEmail = "test#@example.com"

        // Act
        val result = isEmailValidUseCase.invoke(invalidEmail)

        // Assert
        assertFalse(result)
    }

    @Test
    fun invalidEmailWithMultipleAtSymbols() {
        // Arrange
        val invalidEmail = "test@@example.com"

        // Act
        val result = isEmailValidUseCase.invoke(invalidEmail)

        // Assert
        assertFalse(result)
    }
}