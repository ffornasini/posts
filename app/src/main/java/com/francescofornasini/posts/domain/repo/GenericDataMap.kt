package com.francescofornasini.posts.domain.repo

import kotlinx.coroutines.flow.Flow

class GenericDataMap<K, F : Flow<*>>(private val provider: (K) -> F) {
    private val flowMap = mutableMapOf<K, F>()
    operator fun get(id: K) = flowMap.getOrPut(id) { provider(id) }
}