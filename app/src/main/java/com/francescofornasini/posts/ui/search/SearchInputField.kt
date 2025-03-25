package com.francescofornasini.posts.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescofornasini.posts.R
import com.francescofornasini.posts.ui.theme.PostsTheme

/**
 * Composable function for a customizable search input field with dynamic behaviors.
 *
 * @param query The current search query as a nullable string. Used to initialize the text field's value.
 * @param searchBarExpanded Boolean indicating whether the search bar is currently expanded.
 * @param onSearchBarExpandedChange Callback function invoked to toggle the expansion state of the search bar.
 * @param onQueryChange Callback function invoked when the search query is updated.
 * @param onMoreSelect Callback function invoked when the "more options" icon is clicked while the search bar is collapsed.
 *
 * - Displays a [TextField] for entering search queries.
 * - Includes leading and trailing icons for navigation and actions:
 *   - Leading icon changes based on whether the search bar is expanded (e.g., arrow back or more options).
 *   - Trailing icon dynamically switches between search, clear, or other actions based on the current state.
 * - Allows dynamic interaction with the search bar, enabling focus and handling keyboard events.
 * - Uses a transparent design for the container and indicators to integrate seamlessly into various UI themes.
 *
 * - Automatically focuses the text field when the search bar is expanded.
 * - Clears the text field's content or updates the query based on user actions.
 * - Responds to keyboard action `ImeAction.Done` and handles `Key.Enter` event for query submission.
 */
@Composable
fun SearchInputField(
    query: String?,
    searchBarExpanded: Boolean,
    onSearchBarExpandedChange: (Boolean) -> Unit,
    onQueryChange: (String?) -> Unit,
    onMoreSelect: () -> Unit
) {
    var textFieldValue by remember(query, searchBarExpanded) {
        mutableStateOf(
            TextFieldValue(
                text = query.orEmpty(),
                selection = TextRange(query.orEmpty().length)
            )
        )
    }
    val focusRequester = remember { FocusRequester() }
    val focusedTextColor = TextFieldDefaults.colors().focusedTextColor

    LaunchedEffect(searchBarExpanded) {
        if (searchBarExpanded) {
            focusRequester.requestFocus()
        } else {
            textFieldValue = textFieldValue.copy(selection = TextRange(0))
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
                    Icons.Default.MoreVert
                },
                contentDescription = stringResource(
                    if (searchBarExpanded) {
                        R.string.action_back
                    } else {
                        R.string.action_more
                    }
                ),
                tint = focusedTextColor,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        if (searchBarExpanded) {
                            onSearchBarExpandedChange(false)
                        } else {
                            onMoreSelect()
                        }
                    }
                    .padding(8.dp)
            )
        },
        trailingIcon = {
            val showSearch = !searchBarExpanded || textFieldValue.text.isEmpty()

            Icon(
                imageVector = if (showSearch) {
                    Icons.Default.Search
                } else {
                    Icons.Default.Clear
                },
                contentDescription = stringResource(
                    if (showSearch) {
                        R.string.action_search
                    } else {
                        R.string.action_clear
                    }
                ),
                tint = focusedTextColor,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        if (showSearch) {
                            onQueryChange(null)
                            onSearchBarExpandedChange(!searchBarExpanded)
                        } else {
                            textFieldValue = textFieldValue.copy(text = "")
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
                onQueryChange(textFieldValue.text)
                onSearchBarExpandedChange(false)
            },
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledTextColor = focusedTextColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .clip(RoundedCornerShape(28.dp))
            .clickable(enabled = !searchBarExpanded) {
                onSearchBarExpandedChange(true)
            }
            .onKeyEvent {
                if (it.key != Key.Enter) {
                    return@onKeyEvent false
                }
                onQueryChange(textFieldValue.text)
                onSearchBarExpandedChange(false)
                true
            },
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchInputFieldPreview() {
    PostsTheme {
        Surface {
            SearchInputField(
                query = "",
                searchBarExpanded = false,
                onQueryChange = {},
                onSearchBarExpandedChange = {},
                onMoreSelect = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchInputFieldExpandedPreview() {
    PostsTheme {
        Surface {
            SearchInputField(
                query = "You can try me in interactive mode",
                searchBarExpanded = true,
                onQueryChange = {},
                onSearchBarExpandedChange = {},
                onMoreSelect = {}
            )
        }
    }
}
