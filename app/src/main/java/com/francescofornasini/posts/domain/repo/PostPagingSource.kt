package com.francescofornasini.posts.domain.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.francescofornasini.posts.domain.net.Api
import com.francescofornasini.posts.domain.vo.Post
import retrofit2.HttpException
import java.io.IOException

/**
 * A custom implementation of [PagingSource] for loading pages of posts from an API.
 *
 * @param api The [Api] instance used to fetch posts data.
 * @param pageSize The number of posts to load per page.
 * @param query An optional search query to filter the posts.

 * Supports paginated fetching of posts from the API.
 * Handles API errors gracefully, emitting appropriate error results.
 * Dynamically adjusts keys for navigating between pages based on loaded data.
 *
 * Adapted from https://developer.android.com/reference/kotlin/androidx/paging/PagingSource
 */
class PostPagingSource(
    private val api: Api,
    private val pageSize: Int,
    private val query: String?
) : PagingSource<Int, Post>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {

        return try {
            val pageNumber = params.key ?: 0

            val response =
                api.getPosts(start = pageNumber * pageSize, size = pageSize, query = query)
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null

            val nextKey = if (response.size < pageSize) null else pageNumber + 1
            LoadResult.Page(
                itemsBefore = pageNumber * pageSize,
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}