package com.example.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.auth.impl.presentation.ui.SignUpScreen
import com.example.navigation.navigateToFolders
import com.example.navigation.navigateToSignIn
import com.example.shared.feature.auth.ui.SignUpViewModel
import com.example.shared.feature.auth.ui.model.SignUpContract.Effect
import com.example.shared.feature.auth.ui.model.SignUpContract.Event
import com.example.shared.feature.auth.ui.model.SignUpContract.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreenDestination(navController: NavHostController) {
    val viewModel: SignUpViewModel = koinViewModel()
    SignUpScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
    ) { effect ->
        when (effect) {
            is Effect.Navigation.ToFolders -> {
                navController.navigateToFolders(
                    effect.userId,
                )
            }

            Effect.Navigation.ToSignIn -> navController.navigateToSignIn()
        }
    }
}
