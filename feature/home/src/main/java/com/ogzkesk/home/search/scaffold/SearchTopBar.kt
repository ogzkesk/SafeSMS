package com.ogzkesk.home.search.scaffold

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ogzkesk.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchTopBar(
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onNavigate: (String?) -> Unit,
) {

    TopAppBar(
        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .focusRequester(focusRequester),
                    value = searchText,
                    onValueChange = onSearchTextChanged,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    maxLines = 1,
                )

                if (searchText.isNotEmpty()) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            onSearchTextChanged("")
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        actions = {

            TextButton(
                colors = ButtonDefaults
                    .textButtonColors(contentColor = Color.Black.copy(alpha = 0.8f)),
                onClick = {
                    focusManager.clearFocus()
                    onNavigate(null)
                },
                content = {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        style = MaterialTheme.typography.bodyMedium
                            .copy(fontWeight = FontWeight.SemiBold),
                    )
                }
            )
        }
    )
}