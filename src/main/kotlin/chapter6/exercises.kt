package chapter6

import kotlin.math.abs

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

data class SimpleRNG(val seed: Long) : RNG {
    override fun nextInt(): Pair<Int, RNG> {
        val newSeed = (seed * 0x5DEECE66DL + 0xBL) and 0xFFFFFFFFFFFFL
        val nextRNG = SimpleRNG(newSeed)
        val n = (newSeed ushr 16).toInt()
        return n to nextRNG
    }
}

fun nonNegativeInt(rng: RNG): Pair<Int, RNG> {
    val (n, rng2) = rng.nextInt()
    val result = if (n >= 0) n else if (n == Int.MIN_VALUE) 0 else abs(n)
    return result to rng2
}
