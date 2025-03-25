package com.francescofornasini.posts.ui.common

/**
 *  Represents a generic resource state, designed to encapsulate data and its associated state.
 */
sealed class Resource<out T>(
    open val data: T?,
)

data class SuccessResource<out T>(override val data: T?) : Resource<T>(data)
data class LoadingResource<out T>(override val data: T?) : Resource<T>(data)
data class ErrorResource<out T>(override val data: T?, val error: Throwable) : Resource<T>(data)
