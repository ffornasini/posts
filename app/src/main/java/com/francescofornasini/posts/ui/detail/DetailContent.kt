package com.francescofornasini.posts.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.francescofornasini.posts.R
import com.francescofornasini.posts.domain.vo.Post
import com.francescofornasini.posts.ui.common.LoadingResource
import com.francescofornasini.posts.ui.common.Resource

@Composable
fun DetailContent(
    post: Resource<Post>,
    isFavorite: Boolean?,
    toggleFavoriteResult: Resource<*>?,
    onToggleFavorite: () -> Unit
) {

    Scaffold(
        topBar = {
            Button(
                enabled = toggleFavoriteResult !is LoadingResource,
                onClick = onToggleFavorite
            ) {
                if (isFavorite != null) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.action_favorite)
                    )
                }
            }
        }
    ) { padding ->

        Text(text = post.data?.body.orEmpty(), modifier = Modifier.padding(padding))

    }



}