package com.francescofornasini.posts.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.francescofornasini.posts.ui.common.BrandNavigationBar
import com.francescofornasini.posts.ui.common.rememberResult
import com.francescofornasini.posts.ui.detail.Detail
import kotlinx.serialization.Serializable

@Serializable
object Search


@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    var query by rememberSaveable { mutableStateOf<String?>(null) }
    val posts = searchViewModel.posts[query].collectAsLazyPagingItems()
    val hints by searchViewModel.hints.collectAsStateWithLifecycle()

    val (_, addHint) = rememberResult { hint: String ->
        searchViewModel.addHint(hint)
    }

    val (_, clearAllHints) = rememberResult { ->
        searchViewModel.clearAllHints()
    }

    SearchContent(
        query = query,
        posts = posts,
        hints = hints,
        onQueryChange = {
            val newQuery = it?.trim()?.ifBlank { null }
            query = newQuery
            newQuery?.let(addHint)
        },
        onClearAllHints = clearAllHints,
        onPostSelect = { navController.navigate(Detail(it)) },
        navigationBar = { BrandNavigationBar(navController) }
    )
}