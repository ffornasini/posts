package com.francescofornasini.posts.ui.favorite

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object Favorite

@Composable
fun FavoriteScreen() {
    FavoriteContent()
}