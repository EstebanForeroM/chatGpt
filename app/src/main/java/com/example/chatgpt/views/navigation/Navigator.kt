package com.example.chatgpt.views.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatgpt.views.ChatWindow
import com.example.chatgpt.views.LoginWindow

@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            LoginWindow(navController)
        }

        composable("chat") {
            ChatWindow(navController)
        }
    }
}