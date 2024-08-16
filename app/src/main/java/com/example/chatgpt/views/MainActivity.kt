package com.example.chatgpt.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.chatgpt.views.navigation.Navigator
import com.example.chatgpt.views.theme.ChatGptTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false // or false
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = false // or false
        setContent {
            ChatGptTheme {
                Navigator()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatGptTheme {
        Navigator()
    }
}