package chapter6

import chapter3.Cons
import chapter3.List
import chapter3.Nil
import kotlin.math.abs

interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

typealias Rand<A> = (RNG) -> Pair<A, RNG>

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

fun ints(count: Int, rng: RNG): Pair<List<Int>, RNG> =
    if (count == 0)
        Nil to rng
    else {
        val (i, rng2) = nonNegativeInt(rng)
        val (xs, rng3) = ints(count - 1, rng2)
        Cons(i, xs) to rng3
    }

fun <A, B> map(s: Rand<A>, f: (A) -> B): Rand<B> =
    { rng ->
        val (a, rng2) = s(rng)
        f(a) to rng2
    }

fun doubleR(): Rand<Double> =
    map(::nonNegativeInt) { it.toDouble() / Int.MAX_VALUE.toDouble() }
