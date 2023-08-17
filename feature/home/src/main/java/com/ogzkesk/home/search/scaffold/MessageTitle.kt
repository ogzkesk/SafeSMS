package com.ogzkesk.home.search.scaffold

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ogzkesk.core.R

fun LazyListScope.messageTitle(@StringRes title: Int, show: Boolean) {
    item {
        if (show) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(id = title),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = if(title == R.string.messages) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Red
                }
            )
        }
    }
}