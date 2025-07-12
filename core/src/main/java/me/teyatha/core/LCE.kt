package me.teyatha.core

sealed interface LCE<out T> {
    data object Loading : LCE<Nothing>

    data class Content<T>(
        val value: T,
    ) : LCE<T>

    data object Error : LCE<Nothing>
}