package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.designsystem.NotesTheme
import com.example.navigation.NavigationApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0x51F3F2F8)
                ) {

                    NavigationApp()

                }
            }
        }
    }

}

@Composable
fun HomeScreen(navigateToFolders: () -> Unit) {
    Button(
        onClick = {
            navigateToFolders()
        },
    ) {
        Text("Hello!")
    }
}
