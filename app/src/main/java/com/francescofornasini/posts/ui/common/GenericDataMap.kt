package com.francescofornasini.posts.ui.common

import kotlinx.coroutines.flow.Flow

/**
 *  A generic class that manages a mapping of keys to Flow objects, utilizing lazy initialization.
 */
class GenericDataMap<K, F : Flow<*>>(private val provider: (K) -> F) {
    private val flowMap = mutableMapOf<K, F>()
    operator fun get(id: K) = flowMap.getOrPut(id) { provider(id) }
}