package com.ogzkesk.home.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
internal fun FloatActionButton(onNavigate: (String) -> Unit) {
    FloatingActionButton(
        onClick = { onNavigate("chat") }
    ) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
    }
}