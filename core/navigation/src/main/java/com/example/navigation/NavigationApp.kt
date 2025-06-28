package com.example.navigation

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigation.Navigation.Args.FOLDERS
import com.example.navigation.Navigation.Args.FOLDER_ID
import com.example.navigation.Navigation.Args.NOTE
import com.example.navigation.Navigation.Args.NOTES
import com.example.navigation.Navigation.Args.NOTE_ID
import com.example.navigation.Navigation.Args.USER_ID
import com.example.navigation.Navigation.Routes.FOLDERS_PATH
import com.example.navigation.Navigation.Routes.NOTES_PATH
import com.example.navigation.Navigation.Routes.NOTE_PATH
import com.example.navigation.Navigation.Routes.SIGN_IN
import com.example.navigation.Navigation.Routes.SIGN_UP
import com.example.navigation.destination.FoldersScreenDestination
import com.example.navigation.destination.NoteScreenDestination
import com.example.navigation.destination.NotesScreenDestination
import com.example.navigation.destination.SignInScreenDestination
import com.example.navigation.destination.SignUpScreenDestination
import com.example.shared.feature.auth.ui.SignInViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationApp() {

    val navController = rememberNavController()
    val authViewModel: SignInViewModel = koinViewModel()

    val authUserId by authViewModel.userId.collectAsState()

    val currentRoute =
        navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)

    LaunchedEffect(currentRoute.value?.destination?.route) {
        val screenName = currentRoute.value?.destination?.route ?: "unknown_screen"
        logScreenView(screenName)
    }

    NavHost(navController = navController, startDestination = SIGN_IN) {
        composable(route = SIGN_IN) {

            if (authUserId != null) {
                FoldersScreenDestination(navController = navController, userId = authUserId!!)
            } else {
                SignInScreenDestination(navController = navController)
            }
        }

        composable(route = SIGN_UP) {
            SignUpScreenDestination(navController = navController)
        }

        composable(
            route = FOLDERS_PATH,
            arguments = listOf(
                navArgument(name = USER_ID) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val userId =
                requireNotNull(navBackStackEntry.arguments?.getString(USER_ID)) { "User id is required as an argument" }
            FoldersScreenDestination(navController = navController, userId = userId)

        }

        composable(
            route = NOTES_PATH,
            arguments = listOf(
                navArgument(name = FOLDER_ID) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val folderId =
                requireNotNull(navBackStackEntry.arguments?.getString(FOLDER_ID)) { "Folder id is required as an argument" }
            NotesScreenDestination(navController = navController, folderId = folderId)
        }

        composable(
            route = NOTE_PATH,
            arguments = listOf(
                navArgument(name = FOLDER_ID) { type = NavType.StringType },
                navArgument(name = NOTE_ID) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val folderId =
                requireNotNull(navBackStackEntry.arguments?.getString(FOLDER_ID)) { "Folder id is required as an argument" }
            val noteId =
                requireNotNull(navBackStackEntry.arguments?.getString(NOTE_ID)) { "Note id is required as an argument" }
            NoteScreenDestination(
                navController = navController,
                folderId = folderId,
                noteId = noteId
            )
        }
    }

}

fun logScreenView(route: String) {
    val firebaseAnalytics = Firebase.analytics

    val screenName = when (route) {
        SIGN_IN -> {
            SIGN_IN
        }

        SIGN_UP -> {
            SIGN_UP
        }

        FOLDERS_PATH -> {
            FOLDERS
        }

        NOTE_PATH -> {
            NOTE
        }

        NOTES_PATH -> {
            NOTES
        }

        else -> {
            "unknown_screen"
        }
    }

    val bundle = Bundle().apply {
        putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
    }
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    firebaseAnalytics.logEvent("launch_$screenName", null)
    Log.d("LOG EVEN ANALYTICS", "launch_$screenName")
}

object Navigation {

    object Args {
        const val USER_ID = "userId"
        const val FOLDER_ID = "folder_id"
        const val NOTE_ID = "note_id"
        const val NOTES = "notes"
        const val FOLDERS = "folders"
        const val NOTE = "note"
    }

    object Routes {
        const val SIGN_IN = "sign_in"
        const val SIGN_UP = "sign_up"
        const val FOLDERS_PATH = "{${USER_ID}}/$FOLDERS"
        const val NOTE_PATH = "{${FOLDER_ID}}/$NOTES/{${NOTE_ID}}"
        const val NOTES_PATH = "$FOLDERS/{${FOLDER_ID}}"

    }
}

fun NavController.navigateToNotes(folderId: String) {
    navigate(route = "$FOLDERS/$folderId")
}

fun NavController.navigateToNote(folderId: String, noteId: String) {
    Log.d("NAVIGATION", "TO NOTE IN NAV FUN ID = $noteId")
    navigate(route = "$folderId/$NOTES/$noteId")
}

fun NavController.navigateToFolders(userId: String) {
    navigate(route = "$userId/$FOLDERS") {
        popUpTo(0)
    }
}

fun NavController.navigateToSignUp() {
    Log.d("NAVIGATION", "navigateToSignUp")
    navigate(route = SIGN_UP)
}

fun NavController.navigateToSignIn() {
    Log.d("NAVIGATION", "navigateToSignIn")
    navigate(route = SIGN_IN)
}
