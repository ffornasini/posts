package com.francescofornasini.posts.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francescofornasini.posts.domain.repo.PostRepository
import com.francescofornasini.posts.domain.vo.Post
import com.francescofornasini.posts.ui.common.ErrorResource
import com.francescofornasini.posts.ui.common.GenericDataMap
import com.francescofornasini.posts.ui.common.LoadingResource
import com.francescofornasini.posts.ui.common.SuccessResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val allFavorites = postRepository.getFavorites()

    val posts = GenericDataMap { id: Long ->
        flow {
                // first value instantly to bind loading ui
                emit(LoadingResource(null))

                // cached value from favorites
                val favorite = postRepository.getFavorite(id)
                favorite?.let { emit(LoadingResource(favorite)) }

            try {
                // api value
                emit(SuccessResource(postRepository.getPost(id)))
            } catch (e: Exception) {
                emit(ErrorResource(favorite, e))
                if (e is CancellationException) throw e
            }
        }
            .catch { emit(ErrorResource(null, it)) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoadingResource(null))
    }

    val favorites = GenericDataMap { id: Long ->
        allFavorites
            .map { list -> list.any { it.id == id } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }

    suspend fun addFavorite(post: Post) = postRepository.addFavorite(post)
    suspend fun removeFavorite(id: Long) = postRepository.removeFavorite(id)
}