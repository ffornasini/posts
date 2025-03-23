@file:OptIn(ExperimentalMaterial3Api::class)

package com.francescofornasini.posts.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
    query: String,
    posts: LazyPagingItems<Post>,
    onQueryChange: (String) -> Unit,
    navigationBar: @Composable () -> Unit
) {

    var searchBarExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchBar(
                inputField = {
                    Text("input", modifier = Modifier.clickable { searchBarExpanded = true })
                },
                expanded = searchBarExpanded,
                onExpandedChange = { searchBarExpanded = it }
            ) { }
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
                Text(text = "content${post?.id}", modifier = Modifier.padding(16.dp))
            }
        }
    }
}