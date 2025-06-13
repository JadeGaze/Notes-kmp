package com.example.auth.impl.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.auth.api.usecase.validators.IsNotEmptyStringUseCase
import com.example.auth.impl.presentation.model.SignInContract.Effect
import com.example.auth.impl.presentation.model.SignInContract.Event
import com.example.auth.impl.presentation.model.SignInContract.UiState
import com.example.designsystem.BaseViewModel
import com.example.shared.feature.auth.domain.usecase.IsAuthUseCase
import com.example.shared.feature.auth.domain.usecase.SignInUseCase
import com.example.shared.feature.auth.ui.SignInViewModel
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModelImpl(
    private val signInUseCase: SignInUseCase,
    private val isAuthUseCase: IsAuthUseCase,
    private val isNotEmptyStringUseCase: IsNotEmptyStringUseCase,
) : BaseViewModel<Event, UiState, Effect>(), SignInViewModel {

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    init {
        isAuth()
    }

    override fun setInitialState(): UiState {
        return UiState(
            errorMessage = "",
            isLoading = false,
            isError = false,
            isWrongEmail = false,
            isWrongPassword = false
        )
    }

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnSignInClicked -> signIn(event.email, event.password)
            Event.OnSignUpClicked -> setEffect { Effect.Navigation.ToSignUp }
            Event.MessageWasShowed -> setState {
                copy(
                    isLoading = false,
                    isError = false,
                    errorMessage = ""
                )
            }
        }
    }

    override fun signIn(email: String, password: String) {
        if (validate(email, password)) {
            viewModelScope.launch {
                setState {
                    copy(
                        errorMessage = "",
                        isLoading = true,
                        isError = false,
                        isWrongEmail = false,
                        isWrongPassword = false
                    )
                }
                signInUseCase.invoke(email, password)
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
                                        errorMessage = "wrong credentials"
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

    override fun validate(email: String, password: String): Boolean {
        validateEmail(email)
        validatePassword(password)
        return !viewState.value.isWrongEmail && !viewState.value.isWrongPassword
    }

    override fun validateEmail(email: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isNotEmptyStringUseCase.invoke(email)
        setState { copy(isLoading = false, isWrongEmail = !isValid) }
    }

    override fun validatePassword(password: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isNotEmptyStringUseCase.invoke(password)
        setState { copy(isLoading = false, isWrongPassword = !isValid) }
    }

    override fun isAuth() {
        viewModelScope.launch {
            isAuthUseCase.invoke()
                .onSuccess { userId ->
                    _userId.value = userId
                }
                .onFailure {
                    Log.d("SIGN IN", "${it.message}")
                }
        }
    }
}