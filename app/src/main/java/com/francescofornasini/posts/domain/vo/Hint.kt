package com.francescofornasini.posts.domain.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hint")
data class DbHint(
    @PrimaryKey
    val text: String,
    val time: Long
)