package com.francescofornasini.posts.ui.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.francescofornasini.posts.domain.vo.Post
import com.francescofornasini.posts.ui.common.PostItem

@Composable
fun FavoriteContent(
    posts: List<Post>,
    onPostSelect: (Long) -> Unit,
    navigationBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = {},
        bottomBar = navigationBar
    ) { padding ->

        LazyColumn(
            contentPadding = padding
        ) {
            items(posts) { post ->
                PostItem(
                    post = post,
                    modifier = Modifier.clickable {
                        onPostSelect(post.id)
                    }
                )
            }
        }
    }
}