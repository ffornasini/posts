package com.francescofornasini.posts.favorite

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object Favorite

@Composable
fun FavoriteScreen() {
    FavoriteContent()
}