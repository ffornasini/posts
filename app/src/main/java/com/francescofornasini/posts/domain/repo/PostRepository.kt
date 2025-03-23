package com.francescofornasini.posts.domain.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.francescofornasini.posts.domain.db.Dao
import com.francescofornasini.posts.domain.net.Api
import com.francescofornasini.posts.domain.vo.Post
import com.francescofornasini.posts.domain.vo.toDbPost
import javax.inject.Inject

private const val PageSize = 24

class PostRepository @Inject constructor(
    private val api: Api,
    private val dao: Dao
) {

    fun getPosts(query: String?) = Pager(
        config = PagingConfig(
            pageSize = PageSize,
            initialLoadSize = PageSize
        ),
        pagingSourceFactory = {
            PostPagingSource(
                api = api,
                pageSize = PageSize,
                query = query
            )
        }
    )

    fun getFavorites() = dao.getPosts()

    suspend fun getPost(id: Long) = api.getPost(id)
    suspend fun addFavorite(post: Post) = dao.insertPost(post.toDbPost())
    suspend fun removeFavorite(id: Long) = dao.deletePost(id)
}
