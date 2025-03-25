package com.francescofornasini.posts.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francescofornasini.posts.domain.repo.PostRepository
import com.francescofornasini.posts.domain.vo.toPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for managing the state of favorite posts and providing them to the UI.
 */
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    postRepository: PostRepository
) : ViewModel() {

    val favorites = postRepository.getFavorites()
        .map { list -> list.map { it.toPost() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}