package com.francescofornasini.posts.domain.net

import com.francescofornasini.posts.domain.vo.Post
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {

    @GET("posts")
    suspend fun getPosts(
        @Query("_start") start: Int,
        @Query("_size") size: Int,
        @Query("body_like") query: String?
    ): List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Long): List<Post>
}
