package com.francescofornasini.posts.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.francescofornasini.posts.domain.repo.HintRepository
import com.francescofornasini.posts.domain.repo.PostRepository
import com.francescofornasini.posts.ui.common.GenericDataMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for managing the state and logic of the search screen, including posts and hints.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val hintRepository: HintRepository
) : ViewModel() {

    val posts = GenericDataMap { query: String? ->
        postRepository.getPosts(query).flow
            .cachedIn(viewModelScope)
    }

    val hints = hintRepository.getHints()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun addHint(hint: String) = hintRepository.addHint(hint)
    suspend fun clearAllHints() = hintRepository.clearAllHints()
}