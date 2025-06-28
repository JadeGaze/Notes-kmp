package com.example.auth.impl.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.designsystem.SIDE_EFFECTS_KEY
import com.example.designsystem.component.ErrorIconShow
import com.example.designsystem.component.FormButton
import com.example.designsystem.component.FormField
import com.example.designsystem.theme.Typography
import com.example.shared.feature.auth.ui.model.SignUpContract.Effect
import com.example.shared.feature.auth.ui.model.SignUpContract.Event
import com.example.shared.feature.auth.ui.model.SignUpContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SignUpScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
) {
    val name = remember {
        mutableStateOf("")
    }
    val isErrorName = remember {
        mutableStateOf(state.isWrongName)
    }
    val email = remember {
        mutableStateOf("")
    }
    val isErrorEmail = remember {
        mutableStateOf(state.isWrongEmail)
    }
    val password = remember {
        mutableStateOf("")
    }
    val isErrorPassword = remember {
        mutableStateOf(state.isWrongPassword)
    }

    when {
        state.isError -> {
            Toast.makeText(LocalContext.current, state.errorMessage, Toast.LENGTH_LONG).show()
            onEventSent(Event.MessageWasShowed)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 52.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Sign up",
            style = Typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        FormField(
            value = name.value,
            modifier = Modifier.padding(top = 16.dp),
            placeholder = "your name",
            onValueChange = { newString -> name.value = newString },
            isError = isErrorName.value,
            supportingText = "Error",
        )

        FormField(
            value = email.value,
            modifier = Modifier.padding(),
            placeholder = "example@mail.com",
            onValueChange = { newString -> email.value = newString },
            isError = isErrorEmail.value,
            supportingText = "Error",
        )
        FormField(
            value = password.value,
            placeholder = "password",
            onValueChange = { newString -> password.value = newString },
            isError = isErrorPassword.value,
            supportingText = "Error",
        )

        FormButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            buttonText = "sign up",
            onClick = {
                onEventSent(
                    Event.OnSignUpClicked(
                        email = email.value,
                        password = password.value,
                        name = name.value,
                    )
                )
            })

        Text(
            text = "Do have an account? Sign in!",
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { onEventSent(Event.OnSignInClicked) },
            style = Typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
        )
    }



    LaunchedEffect(key1 = state.isWrongEmail) {
        isErrorEmail.value = state.isWrongEmail
    }
    LaunchedEffect(key1 = state.isWrongName) {
        isErrorName.value = state.isWrongName
    }
    LaunchedEffect(key1 = state.isWrongPassword) {
        isErrorPassword.value = state.isWrongPassword
    }


    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is Effect.Navigation.ToFolders -> onNavigationRequested(effect)
                is Effect.Navigation.ToSignIn -> onNavigationRequested(effect)
            }
        }?.collect()
    }
}
