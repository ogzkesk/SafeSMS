package com.ogzkesk.chat.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ogzkesk.core.R

@Composable
internal fun BottomAppBar(
    value: String,
    onValueChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    focusRequester: FocusRequester,
) {

    BottomAppBar(
        windowInsets = WindowInsets.ime,
        contentColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        contentPadding = PaddingValues(12.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            shape = CircleShape,
            placeholder = { Text(text = stringResource(id = R.string.message)) },
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = Color.Red,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
        )

        Spacer(modifier = Modifier.width(8.dp))

        FilledTonalIconButton(
            modifier = Modifier.size(48.dp),
            onClick = onSendClick,
            enabled = value.isNotEmpty(),
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            content = {
                Icon(imageVector = Icons.Outlined.Send, contentDescription = null)
            }
        )
    }
}