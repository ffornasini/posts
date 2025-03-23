package com.francescofornasini.posts.ui.favorite

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.francescofornasini.posts.domain.vo.Post

@Composable
fun FavoriteContent(
    posts: List<Post>,
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
                Text(text = post.title.orEmpty())
            }
        }
    }
}