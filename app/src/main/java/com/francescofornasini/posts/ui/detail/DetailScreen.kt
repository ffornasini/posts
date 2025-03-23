package com.francescofornasini.posts.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.francescofornasini.posts.ui.common.SuccessResource
import com.francescofornasini.posts.ui.common.rememberResult
import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val id: Long
)

@Composable
fun DetailScreen(
    route: Detail,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val post by detailViewModel.posts[route.id].collectAsStateWithLifecycle()
    val isFavorite by detailViewModel.favorites[route.id].collectAsStateWithLifecycle()

    val (toggleFavoriteResult, toggleFavorite) = rememberResult {
        if (post is SuccessResource) {
            val localPost = post.data ?: throw IllegalStateException("null Post")

            when (isFavorite) {
                false -> detailViewModel.addFavorite(localPost)
                true -> detailViewModel.removeFavorite(localPost.id)
                else -> IllegalAccessException("Cannot call addFavorite until favorite status is successfully loaded")
            }

        } else {
            throw IllegalAccessException("Cannot call addFavorite until a post is successfully loaded")
        }
    }

    DetailContent(
        post = post,
        isFavorite = isFavorite,
        toggleFavoriteResult = toggleFavoriteResult,
        onToggleFavorite = toggleFavorite
    )
}