package me.teyatha.core

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

interface Delayer {
    suspend fun delayForABit()
}

internal object KotlinDelayer : Delayer {
    override suspend fun delayForABit() {
        delay(1.seconds)
    }
}