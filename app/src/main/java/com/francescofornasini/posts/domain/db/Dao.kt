package com.francescofornasini.posts.domain.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.francescofornasini.posts.domain.vo.DbHint
import com.francescofornasini.posts.domain.vo.DbPost
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: DbPost)

    @Query("DELETE FROM post WHERE id ==:id")
    suspend fun deletePost(id: Long)

    @Query("SELECT * FROM post")
    fun getPosts(): Flow<List<DbPost>>

    @Query("SELECT * FROM post WHERE id=:id")
    suspend fun getPost(id: Long): DbPost?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHint(hint: DbHint)

    @Query("DELETE FROM hint")
    suspend fun deleteHints()

    @Query("SELECT * FROM hint ORDER BY time ASC")
    fun getHints(): Flow<List<DbHint>>
}
