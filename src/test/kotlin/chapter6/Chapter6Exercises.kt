package chapter6

import chapter3.List
import chapter3.length
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
        assertTrue(t.first > 0.00751 && t.first < 0.00753)
        assertTrue(t.second > 0.59672 && t.second < 0.59674)
        assertTrue(t.third > 0.15845 && t.third < 0.15847)
    }

    @Test
    fun exercise6dot4() {
        val size = 5
        val (xs, _) = ints(size, rng)
        assertEquals(size, xs.length())
        assertEquals(List.of(16159453, 1281479697, 340305902, 2015756020, 1770001318), xs)
    }

    @Test
    fun exercise6dot5() {
        val (d, _) = doubleR()(rng)
        assertTrue(d > 0.00751 && d < 0.00753)
    }

    @Test
    fun exercise6dot7() {
        val r = sequence(List.of(unit(1), unit(2), unit(3)))
        assertEquals(List.of(1, 2, 3), r(rng).first)

        val size = 5
        val (xs, _) = intsViaSequence(size, rng)
        assertEquals(size, xs.length())
        assertEquals(List.of(16159453, 1281479697, 340305902, 2015756020, 1770001318), xs)
    }

    @Test
    fun exercise6dot8() {
        assertEquals(3, nonNegativeLessThan(10)(rng).first)
    }
}
