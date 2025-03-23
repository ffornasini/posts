@file:OptIn(ExperimentalMaterial3Api::class)

package com.francescofornasini.posts.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import com.francescofornasini.posts.ui.common.ErrorResource
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
            Column {
                TopAppBar(
                    title = { Text(stringResource(R.string.section_title_post)) },
                    actions = {
                        if (post.data != null && isFavorite != null) {
                            IconButton(
                                enabled = toggleFavoriteResult !is LoadingResource,
                                onClick = onToggleFavorite
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = stringResource(R.string.action_favorite)
                                )
                            }
                        }
                    }
                )
                if (post is LoadingResource) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    ) { padding ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            if (post is ErrorResource) {
                ElevatedCard(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Box(Modifier.padding(8.dp)) {
                        Text(
                            text = if (post.data != null) {
                                stringResource(R.string.generic_error_with_cache)
                            } else {
                                stringResource(R.string.generic_error)
                            },
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            post.data?.let {
                Text(
                    text = it.title ?: stringResource(R.string.default_post_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    text = it.body ?: stringResource(R.string.default_post_body),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}
