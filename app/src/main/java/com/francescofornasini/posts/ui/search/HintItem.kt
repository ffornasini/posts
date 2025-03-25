package com.francescofornasini.posts.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.francescofornasini.posts.R
import com.francescofornasini.posts.ui.theme.PostsTheme

@Composable
fun HintItem(
    modifier: Modifier = Modifier,
    hint: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = stringResource(R.string.action_history),
            modifier = Modifier.padding(start = 12.dp, end = 16.dp)
        )
        Text(
            text = hint,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(end = 12.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HintItemPreview() {
    PostsTheme {
        Surface {
            HintItem(hint = "hint")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HintItemLongPreview() {
    PostsTheme {
        Surface {
            HintItem(
                hint = LoremIpsum(20).values.joinToString(" "),
            )
        }
    }
}
