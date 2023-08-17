package com.ogzkesk.home.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.ogzkesk.core.ui.navigation.Routes

@Composable
fun FloatActionButton(onNavigate: (String) -> Unit) {
    FloatingActionButton(
        onClick = { onNavigate("chat") }
    ) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
    }
}