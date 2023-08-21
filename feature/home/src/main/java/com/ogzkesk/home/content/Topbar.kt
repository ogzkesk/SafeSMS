package com.ogzkesk.home.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ogzkesk.core.R
import com.ogzkesk.core.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBar(
    appBarBehavior: TopAppBarScrollBehavior,
    onNavigate: (String) -> Unit,
) {
    TopAppBar(
        scrollBehavior = appBarBehavior,
        title = {
            Text(
                text = stringResource(id = R.string.home),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        actions = {

            IconButton(onClick = { onNavigate(Routes.Search.route) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }

            IconButton(onClick = { onNavigate(Routes.Settings.route) }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
    )
}