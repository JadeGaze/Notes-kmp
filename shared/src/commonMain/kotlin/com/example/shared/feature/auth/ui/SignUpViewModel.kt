package com.example.shared.feature.auth.ui

import androidx.lifecycle.viewModelScope
import com.example.auth.api.usecase.SignUpUseCase
import com.example.auth.api.usecase.validators.IsEmailValidUseCase
import com.example.shared.core.presentation.BaseViewModel
import com.example.shared.feature.auth.domain.usecase.validators.IsNameValidUseCase
import com.example.shared.feature.auth.domain.usecase.validators.IsPasswordValidUseCase
import com.example.shared.feature.auth.ui.model.SignUpContract.Event
import com.example.shared.feature.auth.ui.model.SignUpContract.Effect
import com.example.shared.feature.auth.ui.model.SignUpContract.UiState
import com.rickclephas.kmp.observableviewmodel.launch
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val isEmailValidUseCase: IsEmailValidUseCase,
    private val isNameValidUseCase: IsNameValidUseCase,
    private val isPasswordValidUseCase: IsPasswordValidUseCase,
) : BaseViewModel<Event, UiState, Effect>() {
    override fun setInitialState(): UiState {
        return UiState(
            isWrongEmail = false,
            isWrongName = false,
            isWrongPassword = false,
            isLoading = false,
            isError = false,
            errorMessage = "",
        )
    }

    override fun handleEvents(event: Event) {
        when (event) {
            Event.OnSignInClicked -> setEffect { Effect.Navigation.ToSignIn }
            is Event.OnSignUpClicked -> {
                signUp(event.email, event.name, event.password)
            }

            Event.MessageWasShowed -> setState {
                copy(
                    isLoading = false,
                    isError = false,
                    errorMessage = ""
                )
            }
        }
    }

    private fun signUp(email: String, name: String, password: String) {
        if (validate(email, name, password)) {
            viewModelScope.launch {
                setState { copy(isLoading = true, isError = false) }
                signUpUseCase.invoke(email, name, password)
                    .onSuccess { user ->
                        setEffect { Effect.Navigation.ToFolders(user.id) }
                    }
                    .onFailure { throwable ->
                        when (throwable) {
                            is FirebaseAuthException -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        isError = true,
                                        errorMessage = "email already exists"
                                    )
                                }
                            }

                            is FirebaseNetworkException -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        isError = true,
                                        errorMessage = "network error"
                                    )
                                }
                            }

                            else -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        isError = true,
                                        errorMessage = "unexpected error"
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun validate(email: String, name: String, password: String): Boolean {
        validateEmail(email)
        validateName(name)
        validatePassword(password)
        return !viewState.value.isWrongEmail && !viewState.value.isWrongName && !viewState.value.isWrongPassword
    }

    private fun validateEmail(email: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isEmailValidUseCase.invoke(email)
        setState { copy(isLoading = false, isWrongEmail = !isValid) }
    }

    private fun validateName(name: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isNameValidUseCase.invoke(name)
        setState { copy(isLoading = false, isWrongName = !isValid) }
    }

    private fun validatePassword(password: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isPasswordValidUseCase.invoke(password)
        setState { copy(isLoading = false, isWrongPassword = !isValid) }
    }
}