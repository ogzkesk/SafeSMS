package com.ogzkesk.core.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val randomColorList by lazy {
    listOf(
        Purple40,
        PurpleGrey80,
        Purple80,
        Pink40,
        Pink80,
        Color.Gray,
        Color.Green.copy(alpha = 0.3f),
        Color.Red.copy(alpha = 0.3f),
        Color.Yellow.copy(alpha = 0.3f),
        Color.Blue.copy(alpha = 0.3f),
        Color.Cyan.copy(alpha = 0.3f),
        Color.Magenta.copy(alpha = 0.3f),
    )
}