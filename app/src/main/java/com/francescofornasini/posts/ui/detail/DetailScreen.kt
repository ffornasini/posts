package com.francescofornasini.posts.ui.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val id: Long
)

@Composable
fun DetailScreen(
    navController: NavController,
    route: Detail
) {
    DetailContent()
}