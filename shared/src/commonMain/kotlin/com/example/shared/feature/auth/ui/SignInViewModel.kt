package com.example.shared.feature.auth.ui

import androidx.lifecycle.viewModelScope
import com.example.auth.api.usecase.validators.IsNotEmptyStringUseCase
import com.example.shared.core.presentation.BaseViewModel
import com.example.shared.feature.auth.domain.usecase.IsAuthUseCase
import com.example.shared.feature.auth.domain.usecase.SignInUseCase
import com.example.shared.feature.auth.ui.model.SignInContract.Event
import com.example.shared.feature.auth.ui.model.SignInContract.UiState
import com.example.shared.feature.auth.ui.model.SignInContract.Effect
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.launch
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val isAuthUseCase: IsAuthUseCase,
    private val isNotEmptyStringUseCase: IsNotEmptyStringUseCase,
) : BaseViewModel<Event, UiState, Effect>() {

    private val _userId = MutableStateFlow<String?>(null)
    @NativeCoroutinesState
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

    private fun signIn(email: String, password: String) {
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

    private fun validate(email: String, password: String): Boolean {
        validateEmail(email)
        validatePassword(password)
        return !viewState.value.isWrongEmail && !viewState.value.isWrongPassword
    }

    private fun validateEmail(email: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isNotEmptyStringUseCase.invoke(email)
        setState { copy(isLoading = false, isWrongEmail = !isValid) }
    }

    private fun validatePassword(password: String) {
        setState { copy(isLoading = true, isError = false) }
        val isValid = isNotEmptyStringUseCase.invoke(password)
        setState { copy(isLoading = false, isWrongPassword = !isValid) }
    }

    fun isAuth() {
        viewModelScope.launch {
            isAuthUseCase.invoke()
                .onSuccess { userId ->
                    _userId.value = userId
                }
//                .onFailure {
//                    Log.d("SIGN IN", "${it.message}")
//                }
        }
    }
}