package com.francescofornasini.posts.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.francescofornasini.posts.ui.common.BrandNavigationBar
import com.francescofornasini.posts.ui.detail.Detail
import kotlinx.serialization.Serializable

@Serializable
object Search


@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    var query by rememberSaveable { mutableStateOf("") }
    val posts = searchViewModel.posts[query.trim().ifBlank { null }].collectAsLazyPagingItems()

    SearchContent(
        query = query,
        posts = posts,
        onQueryChange = { query = it },
        onPostSelect = { navController.navigate(Detail(it)) },
        navigationBar = { BrandNavigationBar(navController) }
    )
}