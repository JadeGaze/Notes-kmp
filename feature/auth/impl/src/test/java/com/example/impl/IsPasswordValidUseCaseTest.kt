package com.example.impl

import com.example.shared.feature.auth.data.usecase.validation.IsPasswordValidUseCaseImpl
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IsPasswordValidUseCaseTest {


    private val isPasswordValidUseCase = IsPasswordValidUseCaseImpl()

    @Test
    fun `valid password with mixed case letters and numbers`() {
        // Arrange
        val validPassword = "Password123"

        // Act
        val result = isPasswordValidUseCase(validPassword)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `password shorter than 8 characters`() {
        // Arrange
        val shortPassword = "Pass12"

        // Act
        val result = isPasswordValidUseCase(shortPassword)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `password without a letter`() {
        // Arrange
        val passwordWithoutLetter = "12345678"

        // Act
        val result = isPasswordValidUseCase(passwordWithoutLetter)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `password without a number`() {
        // Arrange
        val passwordWithoutNumber = "Password"

        // Act
        val result = isPasswordValidUseCase(passwordWithoutNumber)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `empty password`() {
        // Arrange
        val emptyPassword = ""

        // Act
        val result = isPasswordValidUseCase(emptyPassword)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `password with leading or trailing spaces`() {
        // Arrange
        val spacedPassword = " Password123 "

        // Act
        val result = isPasswordValidUseCase(spacedPassword)

        // Assert
        assertFalse(result)
    }
}

