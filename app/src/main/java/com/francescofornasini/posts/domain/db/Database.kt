package com.francescofornasini.posts.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.francescofornasini.posts.BuildConfig
import com.francescofornasini.posts.domain.vo.DbHint
import com.francescofornasini.posts.domain.vo.DbPost

@Database(
    entities = [
        DbPost::class,
        DbHint::class
    ],
    version = BuildConfig.VERSION_CODE,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}