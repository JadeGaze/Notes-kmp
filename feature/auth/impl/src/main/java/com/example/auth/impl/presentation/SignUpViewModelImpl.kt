package com.example.auth.impl.presentation

import androidx.lifecycle.viewModelScope
import com.example.auth.api.usecase.SignUpUseCase
import com.example.auth.api.usecase.validators.IsEmailValidUseCase
import com.example.auth.impl.presentation.model.SignUpContract.Effect
import com.example.auth.impl.presentation.model.SignUpContract.Event
import com.example.auth.impl.presentation.model.SignUpContract.UiState
import com.example.designsystem.BaseViewModel
import com.example.shared.feature.auth.domain.usecase.validators.IsNameValidUseCase
import com.example.shared.feature.auth.domain.usecase.validators.IsPasswordValidUseCase
import com.example.shared.feature.auth.ui.SignUpViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch

class SignUpViewModelImpl(
    private val signUpUseCase: SignUpUseCase,
    private val isEmailValidUseCase: IsEmailValidUseCase,
    private val isNameValidUseCase: IsNameValidUseCase,
    private val isPasswordValidUseCase: IsPasswordValidUseCase,
) : BaseViewModel<Event, UiState, Effect>(), SignUpViewModel {
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

    override fun signUp(email: String, name: String, password: String) {
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

    override fun validate(email: String, name: String, password: String): Boolean {
        validateEmail(email)
        validateName(name)
        validatePassword(password)
        return !viewState.value.isWrongEmail && !viewState.value.isWrongName && !viewState.value.isWrongPassword
    }

    override fun validateEmail(email: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isEmailValidUseCase.invoke(email)
        setState { copy(isLoading = false, isWrongEmail = !isValid) }
    }

    override fun validateName(name: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isNameValidUseCase.invoke(name)
        setState { copy(isLoading = false, isWrongName = !isValid) }
    }

    override fun validatePassword(password: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isPasswordValidUseCase.invoke(password)
        setState { copy(isLoading = false, isWrongPassword = !isValid) }
    }
}