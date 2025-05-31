package com.example.shared.feature.auth.data.usecase.validation

import com.example.auth.api.usecase.validators.IsNotEmptyStringUseCase

class IsNotEmptyStringUseCaseImpl : IsNotEmptyStringUseCase {
    override fun invoke(string: String): Boolean = string.isNotEmpty()
}