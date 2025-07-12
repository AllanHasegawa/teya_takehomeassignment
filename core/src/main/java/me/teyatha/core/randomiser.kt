package me.teyatha.core

import android.os.SystemClock
import kotlin.random.Random

interface Randomiser {
    /**
     * @param[chanceForTrue] A value between 0f and 1f where 1f is 100%.
     */
    fun weightedBoolean(chanceForTrue: Float): Boolean
}

internal class KotlinRandomiser : Randomiser {
    private val random = Random(SystemClock.uptimeMillis())

    override fun weightedBoolean(chanceForTrue: Float): Boolean =
        random.nextFloat() <= chanceForTrue
}