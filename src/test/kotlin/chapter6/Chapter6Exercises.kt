package chapter6

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class Chapter6Exercises {

    private val rng = SimpleRNG(42)

    @Test
    fun exercise6dot1() {
        val (n, _) = nonNegativeInt(rng)
        assertEquals(16159453, n)
    }

    @Test
    fun exercise6dot2() {
        val (d, _) = double(rng)
        assertTrue(d > 0.00751 && d < 0.00753)
    }

    @Test
    fun exercise6dot3() {
        val (p1, _) = intDouble(rng)
        assertEquals(16159453, p1.first)
        assertTrue(p1.second > 0.59672 && p1.second < 0.59674)

        val (p2, _) = doubleInt(rng)
        assertTrue(p2.first > 0.00751 && p2.first < 0.00753)
        assertEquals(1281479697, p2.second)

        val (t, _) = double3(rng)
        println(t)
        assertTrue(t.first > 0.00751 && t.first < 0.00753)
        assertTrue(t.second > 0.59672 && t.second < 0.59674)
        assertTrue(t.third > 0.15845 && t.third < 0.15847)
    }
}
