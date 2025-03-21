package com.francescofornasini.posts.ui.detail

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
object Detail

@Composable
fun DetailScreen() {
    DetailContent()
}