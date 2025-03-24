package com.francescofornasini.posts.domain.repo

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.francescofornasini.posts.domain.net.Api
import com.francescofornasini.posts.domain.vo.Post
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsIterableContainingInOrder.contains
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class PostPagingSourceTest {

    companion object {
        private const val TEST_PAGE_SIZE = 3

        val config = PagingConfig(
            pageSize = TEST_PAGE_SIZE,
            initialLoadSize = TEST_PAGE_SIZE
        )

        val threePosts = listOf(
            Post(
                id = 0,
                title = "post 0",
                body = "body 4"
            ),
            Post(
                id = 1,
                title = "post 1",
                body = "body 1"
            ),
            Post(
                id = 2,
                title = "post 2",
                body = "body 2"
            )
        )

        val twoPosts = listOf(
            Post(
                id = 3,
                title = "post 3",
                body = "body 3"
            ),
            Post(
                id = 4,
                title = "post 4",
                body = "body 4"
            )
        )
    }

    @Test
    fun `it should load the first page`() = runTest {
        val api = mockk<Api>()

        coEvery {
            api.getPosts(any(), any(), null)
        } returns twoPosts

        val pagingSource = PostPagingSource(
            api = api,
            pageSize = TEST_PAGE_SIZE,
            query = null
        )

        val pager = TestPager(
            config = config,
            pagingSource = pagingSource
        )

        val result = pager.refresh() as PagingSource.LoadResult.Page<Int, Post>

        assertThat(result.data, contains(*twoPosts.toTypedArray()))
    }

    @Test
    fun `it should load more than one page`() = runTest {
        val api = mockk<Api>()

        coEvery {
            api.getPosts(0, any(), null)
        } returns threePosts

        coEvery {
            api.getPosts(TEST_PAGE_SIZE, any(), null)
        } returns twoPosts

        val pagingSource = PostPagingSource(
            api = api,
            pageSize = TEST_PAGE_SIZE,
            query = null
        )

        val pager = TestPager(
            config = config,
            pagingSource = pagingSource
        )

        pager.refresh()
        pager.append()

        assertThat(pager.getPages().first().data, contains(*threePosts.toTypedArray()))
        assertThat(pager.getPages()[1].data, contains(*twoPosts.toTypedArray()))
    }

    @Test
    fun `it should not load pages after an error`() = runTest {
        val api = mockk<Api>()

        coEvery {
            api.getPosts(0, any(), null)
        }.throws(
            HttpException(
                Response.error<Any>(
                    500,
                    "error".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        )

        val pagingSource = PostPagingSource(
            api = api,
            pageSize = TEST_PAGE_SIZE,
            query = null
        )

        val pager = TestPager(
            config = config,
            pagingSource = pagingSource
        )

        pager.refresh()
        pager.append()

        coVerify(exactly = 0) {
            api.getPosts(TEST_PAGE_SIZE, any(), null)
        }
    }
}