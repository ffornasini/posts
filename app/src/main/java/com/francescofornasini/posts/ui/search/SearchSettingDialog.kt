package com.francescofornasini.posts.ui.search

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.francescofornasini.posts.R
import com.francescofornasini.posts.ui.theme.PostsTheme

@Composable
fun SearchSettingDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.action_clear)
            )
        },
        title = {
            Text(text = stringResource(R.string.search_setting_clear_title))
        },
        text = {
            Text(text = stringResource(R.string.search_setting_clear_text))
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.search_setting_dismiss))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.search_setting_confirm))
            }
        },
        onDismissRequest = onDismiss
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchSettingDialogPreview() {
    PostsTheme {
        SearchSettingDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}
