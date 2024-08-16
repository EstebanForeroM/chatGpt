package com.example.chatgpt.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.chatgpt.R
import com.example.chatgpt.views.components.ButtonVisibleSpacer
import com.example.chatgpt.views.components.TopVisibleSpacer

@Preview
@Composable
fun LoginWindow(navController: NavHostController = rememberNavController()) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        MainAnimation(modifier = Modifier.fillMaxSize())
        Column {
            TopVisibleSpacer()
            Text(
                text = "Alpha AI",
                color = Color.White,
                style = TextStyle(fontSize = 40.sp, color = Color.White, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { navController.navigate("chat") }, modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(containerColor = Color.White, contentColor = Color.White,
                    disabledContentColor = Color.Red, disabledContainerColor = Color.Green)
            ) {
                Text(text = "Login", color = Color.Black)
            }
            ButtonVisibleSpacer()
        }
    }
}

@Composable
fun MainAnimation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.login_animation))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(composition = composition, progress = { progress }, modifier = modifier)
}