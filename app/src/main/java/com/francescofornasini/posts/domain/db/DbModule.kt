package com.francescofornasini.posts.domain.db

import android.content.Context
import androidx.room.Room
import com.francescofornasini.posts.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(
            context,
            Database::class.java, BuildConfig.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun dao(database: Database): Dao = database.dao()
}