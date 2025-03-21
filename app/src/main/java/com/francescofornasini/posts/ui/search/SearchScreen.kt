package com.francescofornasini.posts.ui.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.serialization.Serializable

@Serializable
object Search


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    SearchContent()
}