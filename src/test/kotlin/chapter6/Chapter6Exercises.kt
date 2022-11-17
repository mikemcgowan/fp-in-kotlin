package chapter6

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Chapter6Exercises {

    @Test
    fun exercise6dot1() {
        val rng = SimpleRNG(42)
        val (n, _) = nonNegativeInt(rng)
        assertEquals(16159453, n)
    }
}
