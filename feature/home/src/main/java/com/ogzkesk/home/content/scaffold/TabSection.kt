package com.ogzkesk.home.content.scaffold

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun TabSection(
    inboxSize: Int,
    spamSize: Int,
    selectedIndex: Int,
    onIndexChanged: (Int) -> Unit
) {

    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(2){
            Tab(
                selected = selectedIndex == it,
                onClick = { onIndexChanged(it) },
                text = {
                    Text(
                        text = if(it == 0) "Inbox ($inboxSize)" else "Spam ($spamSize)",
                        color = if(it == 0) MaterialTheme.colorScheme.primary else Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            )
        }
    }
}