package com.example.navigation.destination

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.auth.impl.presentation.SignInViewModel
import com.example.auth.impl.presentation.model.SignInContract.Effect
import com.example.auth.impl.presentation.ui.SignInScreen
import com.example.navigation.navigateToFolders
import com.example.navigation.navigateToSignUp
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreenDestination(navController: NavHostController) {
    val viewModel: SignInViewModel = koinViewModel()
    SignInScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { effect ->
            when (effect) {
                is Effect.Navigation.ToFolders -> {
                    navController.navigateToFolders(
                        effect.userId,
                    )
                }

                Effect.Navigation.ToSignUp -> navController.navigateToSignUp()
            }
        }
    )
}