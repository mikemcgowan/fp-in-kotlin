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

fun double(rng: RNG): Pair<Double, RNG> {
    val (result, rng2) = nonNegativeInt(rng)
    val x = if (result == Int.MAX_VALUE) result - 1 else result
    return (x.toDouble() / Int.MAX_VALUE.toDouble()) to rng2
}

fun intDouble(rng: RNG): Pair<Pair<Int, Double>, RNG> {
    val (i, rng2) = nonNegativeInt(rng)
    val (d, rng3) = double(rng2)
    return (i to d) to rng3
}

fun doubleInt(rng: RNG): Pair<Pair<Double, Int>, RNG> {
    val (d, rng2) = double(rng)
    val (i, rng3) = nonNegativeInt(rng2)
    return (d to i) to rng3
}

fun double3(rng: RNG): Pair<Triple<Double, Double, Double>, RNG> {
    val (d1, rng2) = double(rng)
    val (d2, rng3) = double(rng2)
    val (d3, rng4) = double(rng3)
    return Triple(d1, d2, d3) to rng4
}
