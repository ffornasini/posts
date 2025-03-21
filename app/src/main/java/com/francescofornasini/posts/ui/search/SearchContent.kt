@file:OptIn(ExperimentalMaterial3Api::class)

package com.francescofornasini.posts.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
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

@Composable
fun SearchContent() {

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
        }
    ) { padding ->
        Text(text = "content", modifier = Modifier.padding(padding))
    }
}