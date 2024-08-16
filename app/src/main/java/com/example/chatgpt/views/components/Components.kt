package com.example.chatgpt.views.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun TopVisibleSpacer() {
    val density = LocalDensity.current
    val statusBarHeightPx = WindowInsets.statusBars.getTop(density)
    val statusBarHeightDp = with(density) { statusBarHeightPx.toDp() }

    Spacer(modifier = Modifier.height(statusBarHeightDp))
}

@Composable
fun ButtonVisibleSpacer() {
    val density = LocalDensity.current
    val bottomInsetPx = WindowInsets.ime.getBottom(density)
        .coerceAtLeast(WindowInsets.navigationBars.getBottom(density))
    val bottomInsetDp = with(density) { bottomInsetPx.toDp() }

    Spacer(modifier = Modifier.height(bottomInsetDp))
}