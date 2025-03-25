package com.francescofornasini.posts.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.francescofornasini.posts.R
import com.francescofornasini.posts.domain.vo.Post
import com.francescofornasini.posts.ui.theme.PostsTheme

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post?
) {
    val title = when {
        post == null -> ""
        post.title?.ifBlank { null } == null -> stringResource(R.string.default_post_title)
        else -> post.title
    }

    val body = when {
        post == null -> ""
        post.body?.ifBlank { null } == null -> stringResource(R.string.default_post_body)
        else -> post.body
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = body,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PostItemPreview() {
    PostsTheme {
        Surface {
            PostItem(
                post = Post(0L, "title", "body"),
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PostItemLongPreview() {
    PostsTheme {
        Surface {
            PostItem(
                post = Post(0L, "title", LoremIpsum(20).values.joinToString(" ")),
            )
        }
    }
}
