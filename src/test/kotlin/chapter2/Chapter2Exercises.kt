package chapter2

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class Chapter2Exercises {

    @Test
    fun exercise2dot1() {
        val expected = listOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
        val actual = (1..10).map { fib(it) }
        assertEquals(expected, actual)
    }

    @Test
    fun exercise2dot2() {
        assertTrue(isSorted(listOf(1, 2, 3)) { a, b -> b > a })
        assertTrue(!isSorted(listOf(2, 1, 3)) { a, b -> b > a })
    }

    @Test
    fun exercise2dot3() {
        val f = curry { x: Int, y: Int -> x + y }
        val g = f(3)
        assertEquals(5, g(2))
        assertEquals(6, g(3))
        assertEquals(10, g(7))
    }

    @Test
    fun exercise2dot4() {
        val f = uncurry { x: Int -> { y: Int -> x + y } }
        assertEquals(5, f(2, 3))
        assertEquals(11, f(3, 8))
    }

    @Test
    fun exercise2dot5() {
        val g = { x: Int -> x.toString() }
        val f = { s: String -> s.repeat(5) }
        val h = compose(f, g)
        assertEquals("33333", h(3))
    }
}
