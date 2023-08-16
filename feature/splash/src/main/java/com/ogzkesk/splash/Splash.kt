package com.ogzkesk.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ogzkesk.core.ui.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun Splash(onNavigate: (String) -> Unit) {

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        onNavigate(Routes.Home.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
        content = {
            Text(
                text = "SAFE SMS",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    )
}