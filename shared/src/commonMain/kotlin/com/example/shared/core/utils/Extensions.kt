package com.example.shared.core.utils

import kotlinx.coroutines.CancellationException

suspend inline fun <R> runSuspendCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

fun String.isDigitsOnly(): Boolean {
    return this.matches(Regex("[0-9]+"))
}