@file:OptIn(ExperimentalMaterial3Api::class)

package com.francescofornasini.posts.ui.favorite

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
import androidx.compose.ui.unit.dp
import com.francescofornasini.posts.R
import com.francescofornasini.posts.domain.vo.Post
import com.francescofornasini.posts.ui.common.PostItem

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