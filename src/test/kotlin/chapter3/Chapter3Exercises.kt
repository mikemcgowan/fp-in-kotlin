package chapter3

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class Chapter3Exercises {

    @Test
    fun exercise3dot1() {
        val xs = List.of(1, 2, 3, 4)
        assertEquals(List.of(2, 3, 4), xs.tail())
        assertEquals(List.of(3, 4), xs.tail().tail())
        assertEquals(List.of(4), xs.tail().tail().tail())
        assertEquals(Nil, xs.tail().tail().tail().tail())
    }

    @Test
    fun exercise3dot2() {
        val xs = List.of(1, 2, 3, 4)
        assertEquals(List.of(9, 2, 3, 4), xs.setHead(9))
        assertEquals(List.of(9), List.of(1).setHead(9))
        assertEquals(Nil, Nil.setHead(9))
    }

    @Test
    fun exercise3dot3() {
        val xs = List.of(1, 2, 3, 4)
        assertEquals(List.of(2, 3, 4), xs.drop(1))
        assertEquals(List.of(3, 4), xs.drop(2))
        assertEquals(List.of(4), xs.drop(3))
        assertEquals(Nil, xs.drop(4))
    }

    @Test
    fun exercise3dot4() {
        val xs = List.of(1, 2, 3, 4, 5, 6, 7, 8)
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8), xs.dropWhile { it < 0 })
        assertEquals(List.of(5, 6, 7, 8), xs.dropWhile { it < 5 })
        assertEquals(List.of(8), xs.dropWhile { it < 8 })
        assertEquals(Nil, xs.dropWhile { it < 9 })
    }

    @Test
    fun exercise3dot5() {
        assertEquals(List.of(1, 2, 3), List.of(1, 2, 3, 4).init())
        assertEquals(List.of(1, 2), List.of(1, 2, 3).init())
        assertEquals(List.of(1), List.of(1, 2).init())
        assertEquals(Nil, List.of(1).init())
    }

    @Test
    fun exercise3dot8() {
        assertEquals(10, List.of(1, 2, 3, 4).foldRight(0) { x, y -> x + y })
        assertEquals(24, List.of(1, 2, 3, 4).foldRight(1) { x, y -> x * y })
        assertEquals(3, List.of(1, 2, 3).length())
        assertEquals(2, List.of(1, 2).length())
        assertEquals(1, List.of(1).length())
        assertEquals(0, Nil.length())

        val expand = arrayOf(
            List.of(1, 2, 3).length(),
            List.of(1, 2, 3).foldRight(0) { _, y -> 1 + y },
            1 + List.of(2, 3).foldRight(0) { _, y -> 1 + y },
            1 + 1 + List.of(3).foldRight(0) { _, y -> 1 + y },
            1 + 1 + 1 + Nil.foldRight(0) { _, y -> 1 + y },
            1 + 1 + 1 + 0,
        )
        assertTrue(expand.all { it == 3 })
    }

    @Test
    fun exercise3dot9() {
        assertEquals(10, List.of(1, 2, 3, 4).foldLeft(0) { x, y -> x + y })
        assertEquals(24, List.of(1, 2, 3, 4).foldLeft(1) { x, y -> x * y })

        val expand = arrayOf(
            List.of(1, 2, 3, 4).foldLeft(0) { x, y -> x + y },
            List.of(2, 3, 4).foldLeft(0 + 1) { x, y -> x + y },
            List.of(2, 3, 4).foldLeft(1) { x, y -> x + y },
            List.of(3, 4).foldLeft(1 + 2) { x, y -> x + y },
            List.of(3, 4).foldLeft(3) { x, y -> x + y },
            List.of(4).foldLeft(3 + 3) { x, y -> x + y },
            List.of(4).foldLeft(6) { x, y -> x + y },
            (Nil as List<Int>).foldLeft(6 + 4) { x, y -> x + y },
            (Nil as List<Int>).foldLeft(10) { x, y -> x + y },
        )
        assertTrue(expand.all { it == 10 })
    }

    @Test
    fun exercise3dot10() {
        assertEquals(0, Nil.sum())
        assertEquals(10, List.of(1, 2, 3, 4).sum())
        assertEquals(15, List.of(1, 2, 3, 4, 5).sum())

        assertEquals(1, Nil.product())
        assertEquals(6, List.of(2, 3).product())
        assertEquals(24, List.of(2, 3, 4).product())

        assertEquals(0, Nil.lengthViaFoldLeft())
        assertEquals(4, List.of('m', 'i', 'k', 'e').lengthViaFoldLeft())
        assertEquals(5, List.of(true, false, true, false, true).lengthViaFoldLeft())
    }
}
