package com.francescofornasini.posts.ui.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.francescofornasini.posts.ui.common.BrandNavigationBar
import com.francescofornasini.posts.ui.detail.Detail
import kotlinx.serialization.Serializable

@Serializable
object Favorite

/**
 * Composable function for displaying the favorites screen, showing a list of favorite posts
 * and supporting navigation to a detailed view of each post.
 *
 * Delegates the rendering of the screen's content to the [FavoriteContent] composable.
 */
@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {

    val posts by favoriteViewModel.favorites.collectAsStateWithLifecycle()

    FavoriteContent(
        posts = posts,
        onPostSelect = { navController.navigate(Detail(it)) },
        navigationBar = { BrandNavigationBar(navController) }
    )
}