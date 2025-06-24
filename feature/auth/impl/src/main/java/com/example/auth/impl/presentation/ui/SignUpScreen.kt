package com.example.auth.impl.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.impl.presentation.model.SignUpContract.Effect
import com.example.auth.impl.presentation.model.SignUpContract.Event
import com.example.auth.impl.presentation.model.SignUpContract.UiState
import com.example.designsystem.SIDE_EFFECTS_KEY
import com.example.designsystem.component.ErrorIconShow
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
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        OutlinedTextField(
            value = name.value,
            modifier = Modifier.padding(top = 16.dp),
            placeholder = {
                Text(text = "your name")
            },
            singleLine = true,
            onValueChange = { newString ->
                name.value = newString
            },
            isError = isErrorEmail.value,
            supportingText = {
                if (isErrorName.value) {
                    Text(text = "Error")
                }
            },
            trailingIcon = {
                ErrorIconShow(isErrorName.value)
            }
        )
        OutlinedTextField(
            value = email.value,
            modifier = Modifier.padding(),
            placeholder = {
                Text(text = "example@mail.com")
            },
            singleLine = true,
            onValueChange = { newString ->
                email.value = newString
            },
            isError = isErrorEmail.value,
            supportingText = {
                if (isErrorEmail.value) {
                    Text(text = "Error")
                }
            },
            trailingIcon = {
                ErrorIconShow(isErrorEmail.value)
            }
        )
        OutlinedTextField(
            value = password.value,
            placeholder = {
                Text(text = "password")
            },
            singleLine = true,
            onValueChange = { newString ->
                password.value = newString
            },
            isError = isErrorPassword.value,
            supportingText = {
                if (isErrorPassword.value) {
                    Text(text = "Error")
                }
            },
            trailingIcon = {
                ErrorIconShow(isErrorPassword.value)
            }
        )
        OutlinedButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(), onClick = {
                onEventSent(
                    Event.OnSignUpClicked(
                        email = email.value,
                        password = password.value,
                        name = name.value,
                    )
                )
            }) {
            Text(text = "sign up", fontSize = 16.sp)
        }
        Text(
            text = "Do have an account? Sign in!",
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { onEventSent(Event.OnSignInClicked) },
            fontSize = 16.sp
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
