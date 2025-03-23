package com.francescofornasini.posts.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.francescofornasini.posts.R

@Composable
fun SearchInputField(
    query: String?,
    searchBarExpanded: Boolean,
    onSearchBarExpandedChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onMoreSelect: () -> Unit
) {
    var textFieldValue by remember(query, searchBarExpanded) { mutableStateOf(query.orEmpty()) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(searchBarExpanded) {
        if (searchBarExpanded) {
            focusRequester.requestFocus()
        }
    }

    TextField(
        value = textFieldValue,
        enabled = searchBarExpanded,
        onValueChange = { textFieldValue = it },
        placeholder = {
            Text(stringResource(R.string.search_placeholder))
        },
        leadingIcon = {
            Icon(
                imageVector = if (searchBarExpanded) {
                    Icons.AutoMirrored.Filled.ArrowBack
                } else {
                    Icons.Default.Search
                },
                contentDescription = stringResource(
                    if (searchBarExpanded) {
                        R.string.action_back
                    } else {
                        R.string.action_search
                    }
                ),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onSearchBarExpandedChange(!searchBarExpanded)
                    }
                    .padding(8.dp)
            )

        },
        trailingIcon = {
            val showMore = !searchBarExpanded || textFieldValue.isEmpty()

            Icon(
                imageVector = if (showMore) {
                    Icons.Default.MoreVert
                } else {
                    Icons.Default.Clear
                },
                contentDescription = stringResource(R.string.action_more),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        if (showMore) {
                            onMoreSelect()
                        } else {
                            textFieldValue = ""
                        }
                    }
                    .padding(8.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onQueryChange(textFieldValue)
                onSearchBarExpandedChange(false)
            },
        ),
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .clickable(enabled = !searchBarExpanded) {
                onSearchBarExpandedChange(true)
            }
            .onKeyEvent {
                if (it.key != Key.Enter) {
                    return@onKeyEvent false
                }
                onQueryChange(textFieldValue)
                onSearchBarExpandedChange(false)
                true
            },
    )
}