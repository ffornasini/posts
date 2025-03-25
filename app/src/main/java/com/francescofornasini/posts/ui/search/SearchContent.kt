@file:OptIn(ExperimentalMaterial3Api::class)

package com.francescofornasini.posts.ui.search

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.francescofornasini.posts.R
import com.francescofornasini.posts.domain.vo.Post
import com.francescofornasini.posts.ui.common.PostItem
import com.francescofornasini.posts.ui.theme.PostsTheme
import kotlinx.coroutines.flow.flowOf

/**
 * Composable function for rendering the search screen's content.
 *
 * @param query The current search query.
 * @param posts A [LazyPagingItems] object containing the paginated list of posts matching the query.
 * @param hints A list of search history hints for quick access to previous queries.
 * @param onQueryChange Callback function invoked when the search query is updated.
 * @param onClearAllHints Callback function invoked to clear all search history hints.
 * @param onPostSelect Callback function invoked when a post is selected. It passes the selected post's ID as a parameter.
 * @param navigationBar A composable function defining the navigation bar at the bottom of the screen.
 *
 * - Dynamically updates the UI based on the state of the query, posts, and search hints.
 * - Ensures smooth animations for expanding/collapsing the search bar.
 * - Displays search history and manages user interactions with search hints.
 * - Handles pagination states, including loading, error, and idle states.
 */
@Composable
fun SearchContent(
    query: String?,
    posts: LazyPagingItems<Post>,
    hints: List<String>,
    onQueryChange: (String?) -> Unit,
    onClearAllHints: () -> Unit,
    onPostSelect: (Long) -> Unit,
    navigationBar: @Composable () -> Unit
) {

    var searchBarExpanded by remember { mutableStateOf(false) }
    var showSettingDialog by remember { mutableStateOf(false) }

    if (showSettingDialog) {
        SearchSettingDialog(
            onConfirm = { onClearAllHints() },
            onDismiss = { showSettingDialog = false }
        )
    }

    Scaffold(
        topBar = {
            /*
                Horizontal padding needs to be applied to the SearchBar, but only when it is collapsed.
                Since the component does not natively support this behavior, padding must be animated
                to maintain the smoothness of the expand/collapse transition.
            */
            val searchBarPadding by animateDpAsState(
                targetValue = if (searchBarExpanded) 0.dp else 16.dp,
                label = "SearchBar padding"
            )

            Column {
                SearchBar(
                    inputField = {
                        SearchInputField(
                            query = query,
                            searchBarExpanded = searchBarExpanded,
                            onQueryChange = onQueryChange,
                            onSearchBarExpandedChange = { searchBarExpanded = it },
                            onMoreSelect = { showSettingDialog = true }
                        )
                    },
                    expanded = searchBarExpanded,
                    onExpandedChange = { searchBarExpanded = it },
                    colors = SearchBarDefaults.colors(
                        dividerColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(horizontal = searchBarPadding)
                ) {
                    LazyColumn(
                        modifier = Modifier.imePadding()
                    ) {
                        if (hints.isNotEmpty())
                            item {
                                Text(
                                    text = stringResource(R.string.search_history),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }

                        items(hints) { hint ->
                            HintItem(
                                hint = hint,
                                modifier = Modifier
                                    .clickable {
                                        onQueryChange(hint)
                                        searchBarExpanded = false
                                    })
                        }
                    }
                }

                if (posts.loadState.refresh == LoadState.Loading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        },
        bottomBar = navigationBar
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(bottom = padding.calculateBottomPadding()),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding() + 8.dp)
        ) {
            // empty view
            if (posts.loadState.isIdle && posts.itemCount == 0) {
                item {
                    Text(
                        text = stringResource(R.string.search_results_empty),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 8.dp)
                    )
                }
            }

            if (posts.itemCount > 0) {
                item {
                    Text(
                        text = stringResource(R.string.search_results),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 8.dp)
                    )
                }
            }

            items(
                count = posts.itemCount,
                key = posts.itemKey { it.id }
            ) { index ->
                val post = posts[index]
                PostItem(
                    post = post,
                    modifier = Modifier
                        .clickable(enabled = post != null) {
                            post?.id?.let { onPostSelect(it) }
                        }
                )
            }

            // error view
            item {
                if (posts.loadState.hasError) {
                    ElevatedCard(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Box(Modifier.padding(8.dp)) {
                            Text(
                                text = stringResource(R.string.generic_pagination_error),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchContentPreview() {

    var query by remember { mutableStateOf<String?>("I'm interactive!") }
    var hints by remember { mutableStateOf<List<String>>(emptyList()) }

    val posts = remember(query) {
        val results = (0L..5L).map {
            Post(it, "title $it", "body $it")
        }.filter { post -> query?.let { post.title?.contains(it) == true } ?: true }

        flowOf(
            PagingData.from(
                data = results,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.NotLoading(true)
                )
            )
        )
    }.collectAsLazyPagingItems()

    PostsTheme {
        SearchContent(
            query = query,
            posts = posts,
            hints = hints,
            onQueryChange = { newQuery ->
                query = newQuery
                newQuery?.trim()?.ifBlank { null }?.let {
                    if (!hints.contains(it)) {
                        hints = hints + it
                    }
                }
            },
            onClearAllHints = { hints = emptyList() },
            onPostSelect = { },
            navigationBar = { }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchContentErrorPreview() {

    val posts = remember {
        flowOf(
            PagingData.empty<Post>(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(IllegalStateException("mock error")),
                    prepend = LoadState.Error(IllegalStateException("mock error")),
                    append = LoadState.Error(IllegalStateException("mock error"))
                )
            )
        )
    }.collectAsLazyPagingItems()

    PostsTheme {
        SearchContent(
            query = null,
            posts = posts,
            hints = emptyList(),
            onQueryChange = { },
            onClearAllHints = { },
            onPostSelect = { },
            navigationBar = { }
        )
    }
}
