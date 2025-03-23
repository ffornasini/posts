@file:OptIn(ExperimentalMaterial3Api::class)

package com.francescofornasini.posts.ui.search

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.francescofornasini.posts.domain.vo.Post

@Composable
fun SearchContent(
    query: String?,
    posts: LazyPagingItems<Post>,
    hints: List<String>,
    onQueryChange: (String) -> Unit,
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
            val searchBarPadding by animateDpAsState(
                targetValue = if (searchBarExpanded) 0.dp else 16.dp,
                label = "SearchBar padding"
            )

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
                modifier = Modifier.padding(horizontal = searchBarPadding)
            ) {
                LazyColumn {
                    items(hints) { hint ->
                        Text(text = hint, modifier = Modifier.clickable {
                            onQueryChange(hint)
                            searchBarExpanded = false
                        })
                    }
                }

            }
        },
        bottomBar = navigationBar
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = posts.itemCount,
                key = posts.itemKey { it.id }
            ) { index ->
                val post = posts[index]
                Text(
                    text = "content${post?.id}", modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            post?.id?.let {
                                onPostSelect(
                                    it
                                )
                            }
                        })
            }
        }
    }
}