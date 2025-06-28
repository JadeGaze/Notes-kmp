package com.example.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.auth.impl.presentation.ui.SignInScreen
import com.example.navigation.navigateToFolders
import com.example.navigation.navigateToSignUp
import com.example.shared.feature.auth.ui.SignInViewModel
import com.example.shared.feature.auth.ui.model.SignInContract.Effect
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreenDestination(navController: NavHostController) {
    val viewModel: SignInViewModel = koinViewModel()
    SignInScreen(
        state = viewModel.viewState.collectAsState().value,
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