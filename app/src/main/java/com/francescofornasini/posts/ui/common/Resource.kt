package com.francescofornasini.posts.ui.common

sealed class Resource<out T>(
    open val data: T?,
)

data class SuccessResource<out T>(override val data: T?) : Resource<T>(data)
data class LoadingResource<out T>(override val data: T?) : Resource<T>(data)
data class ErrorResource<out T>(override val data: T?, val error: Throwable) : Resource<T>(data)
