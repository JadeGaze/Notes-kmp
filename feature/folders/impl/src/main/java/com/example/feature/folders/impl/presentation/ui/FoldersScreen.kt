@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.feature.folders.impl.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.designsystem.R
import com.example.designsystem.SIDE_EFFECTS_KEY
import com.example.designsystem.component.NetworkError
import com.example.designsystem.component.Progress
import com.example.feature.folders.impl.presentation.model.FoldersContract.Effect
import com.example.feature.folders.impl.presentation.model.FoldersContract.Event
import com.example.feature.folders.impl.presentation.model.FoldersContract.UiState
import com.example.feature.folders.impl.presentation.ui.component.ExpandableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun FoldersScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    navHostController: NavHostController,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
) {

    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp),
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.folders)) })
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                var showBottomSheet by remember { mutableStateOf(false) }
                if (showBottomSheet) {
                    NewFolderDialog(onDismissRequest = {
                        showBottomSheet = false
                    }) { folderName, isSync ->
                        onEventSent(Event.OnCreateNewFolderClicked(folderName, isSync))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        modifier = Modifier.clickable {
                            Log.d(FOLDER_SCREEN_TAG, "add folder clicked")
                            showBottomSheet = true
                        },
                        painter = painterResource(id = R.drawable.outline_create_new_folder_24),
                        contentDescription = ""
                    )
                }
            }
        }
    ) { paddingValue ->

        LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
            effectFlow?.onEach { effect ->
                when (effect) {
                    Effect.DataWasLoaded -> {
                        Log.d(FOLDER_SCREEN_TAG, "data was loaded")
                    }

                    is Effect.Navigation.ToNewNote -> onNavigationRequested(effect)
                    is Effect.Navigation.ToNotes -> onNavigationRequested(effect)
                    Effect.FolderWasCreated -> Log.d(FOLDER_SCREEN_TAG, "folder was created")
                    is Effect.DataLoadingError -> Log.d(FOLDER_SCREEN_TAG, effect.message)
                    is Effect.FolderCreateError -> Log.d(FOLDER_SCREEN_TAG, "folder wasn't created")
                    Effect.Empty -> {}
                }
            }?.collect()
        }

        LaunchedEffect(key1 = navHostController) {
            onEventSent(Event.GetData)
        }

        when {
            state.isLoading -> Progress()
            state.isError -> NetworkError { onEventSent(Event.Retry) }
            else -> {
                Log.d(FOLDER_SCREEN_TAG, "data in showData() ${state.sectionData}")

                ExpandableList(
                    modifier = Modifier.padding(paddingValue),
                    sectionData = state.sectionData,
                ) { folderId ->
                    onEventSent(Event.OnFolderClicked(folderId))
                }
            }
        }

    }


}

@Composable
private fun ShowData(
    paddingValue: PaddingValues,
    state: UiState,
    onEventSent: (event: Event) -> Unit,
) {
    ExpandableList(
        modifier = Modifier.padding(paddingValue),
        sectionData = state.sectionData,
    ) { folderId ->
        onEventSent(Event.OnFolderClicked(folderId))
    }
}


const val FOLDER_SCREEN_TAG = "FolderScreenTag"