package com.francescofornasini.posts.domain.repo

import com.francescofornasini.posts.domain.db.Dao
import com.francescofornasini.posts.domain.vo.DbHint
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HintRepository @Inject constructor(
    private val dao: Dao
) {

    fun getHints() = dao.getHints().map { list -> list.map { it.text } }

    suspend fun addHint(hint: String) =
        dao.insertHint(DbHint(text = hint, time = System.currentTimeMillis()))

    suspend fun clearAllHints() = dao.deleteHints()
}