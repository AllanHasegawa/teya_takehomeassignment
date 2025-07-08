package me.teyatha.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderBuilder
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * Adapted from: https://github.com/android/nav3-recipes/blob/main/app/src/main/java/com/example/nav3recipes/modular/hilt/CommonModule.kt
 */
typealias EntryProviderInstaller = EntryProviderBuilder<Any>.() -> Unit

@ActivityRetainedScoped
class Navigator(startDestination: Any) {
    val backStack: SnapshotStateList<Any> = mutableStateListOf(startDestination)

    fun goTo(destination: Any) {
        backStack.add(destination)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}