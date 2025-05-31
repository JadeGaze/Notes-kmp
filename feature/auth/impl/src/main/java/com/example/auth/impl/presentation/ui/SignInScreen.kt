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
import com.example.auth.impl.presentation.model.SignInContract.Effect
import com.example.auth.impl.presentation.model.SignInContract.Event
import com.example.auth.impl.presentation.model.SignInContract.UiState
import com.example.designsystem.SIDE_EFFECTS_KEY
import com.example.designsystem.component.ErrorIconShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
) {


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
            text = "Sign in",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        OutlinedTextField(
            value = email.value,
            modifier = Modifier.padding(top = 16.dp),
            placeholder = {
                Text(text = "login")
            },
            singleLine = true,
            onValueChange = { newString ->
                email.value = newString
            },
            isError = isErrorEmail.value,
            supportingText = {
                if (isErrorEmail.value) {
                    Text(text = "Enter login")
                }
            },
            trailingIcon = {
                ErrorIconShow(isErrorPassword.value)
            }
        )
        OutlinedTextField(
            value = password.value,
            modifier = Modifier
                .padding(top = 16.dp),
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
                    Text(text = "Enter password")
                }
            },
            trailingIcon = {
                ErrorIconShow(isErrorPassword.value)
            })
        OutlinedButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            onClick = { onEventSent(Event.OnSignInClicked(email.value, password.value)) }) {
            Text(text = "sign in", fontSize = 16.sp)
        }
        Text(
            text = "Don't have an account? Sign up!",
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    onEventSent(Event.OnSignUpClicked)
                },
            fontSize = 16.sp
        )
    }

    LaunchedEffect(key1 = state.isWrongEmail) {
        isErrorEmail.value = state.isWrongEmail
    }
    LaunchedEffect(key1 = state.isWrongPassword) {
        isErrorPassword.value = state.isWrongPassword
    }

    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is Effect.Navigation.ToSignUp -> onNavigationRequested(effect)
                is Effect.Navigation.ToFolders -> onNavigationRequested(effect)
            }
        }?.collect()
    }

}
