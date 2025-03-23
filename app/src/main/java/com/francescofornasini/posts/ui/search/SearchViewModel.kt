package com.francescofornasini.posts.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.francescofornasini.posts.domain.repo.HintRepository
import com.francescofornasini.posts.ui.common.GenericDataMap
import com.francescofornasini.posts.domain.repo.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val hintRepository: HintRepository
): ViewModel() {

    val posts = GenericDataMap { query: String? ->
        postRepository.getPosts(query).flow
            .cachedIn(viewModelScope)
    }

    val hints = hintRepository.getHints()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun addHint(hint: String) = hintRepository.addHint(hint)
    suspend fun clearAllHints() = hintRepository.clearAllHints()
}