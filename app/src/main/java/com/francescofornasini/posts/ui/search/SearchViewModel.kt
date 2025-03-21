package com.francescofornasini.posts.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.francescofornasini.posts.domain.repo.GenericDataMap
import com.francescofornasini.posts.domain.repo.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel() {

    val posts = GenericDataMap { query: String? ->
        postRepository.getPosts(query).flow.cachedIn(viewModelScope)
    }
}