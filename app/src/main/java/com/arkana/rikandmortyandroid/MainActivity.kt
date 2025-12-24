package com.arkana.rikandmortyandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkana.rikandmortyandroid.ui.character.screens.CharacterListScreen
import com.arkana.rikandmortyandroid.ui.theme.RikandmortyandroidarkanaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RikandmortyandroidarkanaTheme {
                CharacterListScreen()
            }
        }
    }
}
