package com.francescofornasini.posts.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException


@Composable
fun <T, U> rememberResult(
    fn: suspend (U) -> T
): Pair<Resource<T>?, (U) -> Unit> {
    var result by remember { mutableStateOf<Resource<T>?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val resultFn = { u: U ->
        coroutineScope.launch {
            try {
                result = LoadingResource(null)
                result = SuccessResource(fn(u))
            } catch (e: Exception) {
                result = ErrorResource(null, e)
                if (e is CancellationException) throw e
            }
        }
        Unit // we can also return the job but it gives problems with type inference
    }

    return result to resultFn
}

@Composable
fun <T> rememberResult(
    fn: suspend () -> T
): Pair<Resource<T>?, () -> Unit> {
    val (result, resultFn) = rememberResult<T, Unit> { fn() }
    return result to { resultFn(Unit) }
}