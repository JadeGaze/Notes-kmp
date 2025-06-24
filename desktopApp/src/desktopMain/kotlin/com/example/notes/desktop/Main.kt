package com.example.notes.desktop

import android.app.Application
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.auth.impl.presentation.authPresentationModule
import com.example.designsystem.NotesTheme
import com.example.feature.folders.impl.foldersPresentationModel
import com.example.impl.notePresentationModule
import com.example.navigation.NavigationApp
import com.example.notes.impl.notesPresentationModule
import com.example.shared.dbModule
import com.example.shared.feature.auth.authModule
import com.example.shared.feature.folders.foldersModule
import com.example.shared.feature.note.noteModule
import com.example.shared.feature.notes.notesModule
import com.google.firebase.Firebase
import com.google.firebase.FirebaseOptions
import com.google.firebase.FirebasePlatform
import com.google.firebase.initialize
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okio.Path.Companion.toPath
import org.koin.core.context.startKoin

fun main() {
    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {
        val storage = createDataStore { "firebase.preferences_pb" }
        override fun store(key: String, value: String) {
            runBlocking {
                storage.edit {
                    it[stringPreferencesKey(key)] = value
                }
            }
        }
        override fun retrieve(key: String) = runBlocking {
            storage.data.first()[stringPreferencesKey(key)]
        }
        override fun clear(key: String) {
            runBlocking {
                storage.edit {
                    it -= stringPreferencesKey(key)
                }
            }
        }
        override fun log(msg: String) = println(msg)
    })
    val options = FirebaseOptions.Builder()
        .setProjectId("notes-6ea5a")
        .setApplicationId("1:687297554598:android:fd953be55a871a44faac67")
        .setApiKey("AIzaSyCpa1YdgHs9S39WjkKTAlfc7iSjHlNwS2g")
        .build()
    Firebase.initialize(Application(), options)
    startKoin {
        modules(
            dbModule,

            authModule,
            authPresentationModule,

            foldersModule,
            foldersPresentationModel,

            notesModule,
            notesPresentationModule,

            noteModule,
            notePresentationModule
        )
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Notes",
            state = rememberWindowState(width = 500.dp, height = 800.dp)
        ) {
//        val count = remember { mutableStateOf(0) }

            NotesTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = Color(0x51F3F2F8)
//                ) {

                    NavigationApp()

//                }
//            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
//                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                    onClick = {
//                        count.value++
//                    }) {
//                    Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
//                }
//                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                    onClick = {
//                        count.value = 0
//                    }) {
//                    Text("Reset")
//                }
//            }
            }
        }
    }
}

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )