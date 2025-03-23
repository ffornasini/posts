package com.francescofornasini.posts.ui.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.francescofornasini.posts.ui.common.BrandNavigationBar
import kotlinx.serialization.Serializable

@Serializable
object Favorite

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
    ) {

    val posts by favoriteViewModel.favorites.collectAsStateWithLifecycle()

    FavoriteContent(
        posts = posts,
        navigationBar = { BrandNavigationBar(navController) }
    )
}