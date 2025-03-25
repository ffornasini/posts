@file:OptIn(ExperimentalMaterial3Api::class)

package com.francescofornasini.posts.ui.favorite

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescofornasini.posts.R
import com.francescofornasini.posts.domain.vo.Post
import com.francescofornasini.posts.ui.common.PostItem
import com.francescofornasini.posts.ui.theme.PostsTheme

/**
 * Composable function to display a list of favorite posts with navigation support.
 *
 * @param posts The list of posts to display. If the list is empty, a message indicating
 *              the absence of favorite posts will be shown.
 * @param onPostSelect Callback invoked when a post is selected. The parameter is the ID of the selected post.
 * @param navigationBar A composable function representing the navigation bar to display at the bottom.
 *
 *   - If `posts` is empty, displays a message using [Text].
 *   - If `posts` contains data, iterates through the list and displays each post using [PostItem].
 */
@Composable
fun FavoriteContent(
    posts: List<Post>,
    onPostSelect: (Long) -> Unit,
    navigationBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.section_title_favorite))
                }
            )
        },
        bottomBar = navigationBar
    ) { padding ->
        LazyColumn(
            contentPadding = padding
        ) {
            // empty view
            if (posts.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.favorite_empty),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 8.dp)
                    )
                }
            }

            items(posts) { post ->
                PostItem(
                    post = post,
                    modifier = Modifier
                        .clickable {
                            onPostSelect(post.id)
                        }
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FavoriteContentEmptyPreview() {
    PostsTheme {
        FavoriteContent(
            posts = emptyList(),
            onPostSelect = {},
            navigationBar = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FavoriteContentPreview() {
    val posts = (0L..5L)
        .map { Post(it, "title $it", "body $it") }
    PostsTheme {
        FavoriteContent(
            posts = posts,
            onPostSelect = {},
            navigationBar = {}
        )
    }
}
