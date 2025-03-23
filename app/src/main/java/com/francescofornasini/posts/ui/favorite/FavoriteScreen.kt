package com.francescofornasini.posts.ui.favorite

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.francescofornasini.posts.ui.common.BrandNavigationBar
import kotlinx.serialization.Serializable

@Serializable
object Favorite

@Composable
fun FavoriteScreen(
    navController: NavController,
    ) {

    FavoriteContent(
        navigationBar = { BrandNavigationBar(navController) }
    )
}