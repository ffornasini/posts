package com.francescofornasini.posts.domain.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val title: String?,
    val body: String?
)


@Entity(tableName = "post")
data class DbPost(
    @PrimaryKey
    val id: Long,
    val title: String,
    val body: String
)