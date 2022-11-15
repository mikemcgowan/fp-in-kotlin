package chapter5

import chapter3.List
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Chapter5Exercises {

    @Test
    fun exercise5dot1() {
        assertEquals(List.of(1, 2, 3, 4), Stream.of(1, 2, 3, 4).toList())
    }

    @Test
    fun exercise5dot2() {
        val xs = Stream.of(1, 2, 3, 4, 5)

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
        val xs = Stream.of(1, 2, 3, 4, 5)

        assertEquals(List.empty(), xs.takeWhile { false }.toList())
        assertEquals(List.empty(), xs.takeWhile { it < 1 }.toList())
        assertEquals(List.of(1), xs.takeWhile { it < 2 }.toList())
        assertEquals(List.of(1, 2), xs.takeWhile { it < 3 }.toList())
        assertEquals(List.of(1, 2, 3), xs.takeWhile { it < 4 }.toList())
        assertEquals(List.of(1, 2, 3, 4), xs.takeWhile { it < 5 }.toList())
        assertEquals(List.of(1, 2, 3, 4, 5), xs.takeWhile { it < 6 }.toList())
        assertEquals(List.of(1, 2, 3, 4, 5), xs.takeWhile { true }.toList())
    }
}
