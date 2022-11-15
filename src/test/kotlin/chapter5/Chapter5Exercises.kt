package chapter5

import chapter3.List
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Chapter5Exercises {

    private val xs = Stream.of(1, 2, 3, 4, 5)

    @Test
    fun exercise5dot1() {
        assertEquals(List.of(1, 2, 3, 4, 5), xs.toList())
    }

    @Test
    fun exercise5dot2() {
        assertEquals(List.empty(), xs.take(0).toList())
        assertEquals(List.of(1), xs.take(1).toList())
        assertEquals(List.of(1, 2), xs.take(2).toList())
        assertEquals(List.of(1, 2, 3), xs.take(3).toList())
        assertEquals(List.of(1, 2, 3, 4), xs.take(4).toList())
        assertEquals(List.of(1, 2, 3, 4, 5), xs.take(5).toList())
        assertEquals(List.of(1, 2, 3, 4, 5), xs.take(6).toList())

        assertEquals(List.of(1, 2, 3, 4, 5), xs.drop(0).toList())
        assertEquals(List.of(2, 3, 4, 5), xs.drop(1).toList())
        assertEquals(List.of(3, 4, 5), xs.drop(2).toList())
        assertEquals(List.of(4, 5), xs.drop(3).toList())
        assertEquals(List.of(5), xs.drop(4).toList())
        assertEquals(List.empty(), xs.drop(5).toList())
        assertEquals(List.empty(), xs.drop(6).toList())
    }

    @Test
    fun exercise5dot3() {
        assertEquals(List.empty(), xs.takeWhile { false }.toList())
        assertEquals(List.empty(), xs.takeWhile { it < 1 }.toList())
        assertEquals(List.of(1), xs.takeWhile { it < 2 }.toList())
        assertEquals(List.of(1, 2), xs.takeWhile { it < 3 }.toList())
        assertEquals(List.of(1, 2, 3), xs.takeWhile { it < 4 }.toList())
        assertEquals(List.of(1, 2, 3, 4), xs.takeWhile { it < 5 }.toList())
        assertEquals(List.of(1, 2, 3, 4, 5), xs.takeWhile { it < 6 }.toList())
        assertEquals(List.of(1, 2, 3, 4, 5), xs.takeWhile { true }.toList())
    }

    @Test
    fun exercise5dot4() {
        assertTrue(xs.forAll { it > 0 })
        assertFalse(xs.forAll { it > 1 })
        assertFalse(xs.forAll { it > 5 })
        assertTrue(xs.forAll { it < 10 })

        assertTrue(xs.forAllViaFoldRight { it > 0 })
        assertFalse(xs.forAllViaFoldRight { it > 1 })
        assertFalse(xs.forAllViaFoldRight { it > 5 })
        assertTrue(xs.forAllViaFoldRight { it < 10 })
    }

    @Test
    fun exercise5dot5() {
        assertEquals(List.empty(), xs.takeWhileViaFoldRight { false }.toList())
        assertEquals(List.empty(), xs.takeWhileViaFoldRight { it < 1 }.toList())
        assertEquals(List.of(1), xs.takeWhileViaFoldRight { it < 2 }.toList())
        assertEquals(List.of(1, 2), xs.takeWhileViaFoldRight { it < 3 }.toList())
        assertEquals(List.of(1, 2, 3), xs.takeWhileViaFoldRight { it < 4 }.toList())
        assertEquals(List.of(1, 2, 3, 4), xs.takeWhileViaFoldRight { it < 5 }.toList())
        assertEquals(List.of(1, 2, 3, 4, 5), xs.takeWhileViaFoldRight { it < 6 }.toList())
        assertEquals(List.of(1, 2, 3, 4, 5), xs.takeWhileViaFoldRight { true }.toList())
    }
}
