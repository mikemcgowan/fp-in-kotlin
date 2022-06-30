package chapter3

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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

    @Test
    fun exercise3dot11() {
        assertEquals(List.of(4, 3, 2, 1), List.of(1, 2, 3, 4).reverse())
        assertEquals(Nil, Nil.reverse())
        assertEquals(List.of(9), List.of(9).reverse())
    }

    @Test
    fun exercise3dot13() {
        assertEquals(List.of(1, 2, 3, 4, 5), List.of(1, 2, 3).append(List.of(4, 5)))
    }

    @Test
    fun exercise3dot14() {
        assertEquals(
            List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
            List.concat(
                List.of(
                    List.of(1, 2, 3),
                    List.of(4, 5, 6),
                    List.of(7, 8, 9),
                )
            )
        )
        assertEquals(
            List.of(1, 2, 3, 4, 5, 6, 7, 8, 9),
            List.concatViaAppend(
                List.of(
                    List.of(1, 2, 3),
                    List.of(4, 5, 6),
                    List.of(7, 8, 9),
                )
            )
        )
        assertEquals(
            List.of('M', 'i', 'k', 'e', ' ', 'M', 'c', 'G', 'o', 'w', 'a', 'n'),
            List.concat(
                List.of(
                    List.of('M', 'i', 'k', 'e'),
                    List.of(' '),
                    List.of('M', 'c', 'G', 'o', 'w', 'a', 'n')
                )
            )
        )
        assertEquals(
            List.of('M', 'i', 'k', 'e', ' ', 'M', 'c', 'G', 'o', 'w', 'a', 'n'),
            List.concatViaAppend(
                List.of(
                    List.of('M', 'i', 'k', 'e'),
                    List.of(' '),
                    List.of('M', 'c', 'G', 'o', 'w', 'a', 'n')
                )
            )
        )
    }

    @Test
    fun exercise3dot15() {
        assertEquals(List.of(2, 3, 4, 5), List.of(1, 2, 3, 4).addOne())
    }

    @Test
    fun exercise3dot16() {
        assertEquals(List.of("0.0", "2.5", "5.0", "7.5"), List.of(0.0, 2.5, 5.0, 7.5).stringify())
    }

    @Test
    fun exercise3dot17() {
        assertEquals(List.of(2, 3, 4, 5), List.of(1, 2, 3, 4).map { it + 1 })
        assertEquals(List.of("0.0", "2.5", "5.0", "7.5"), List.of(0.0, 2.5, 5.0, 7.5).map { it.toString() })
    }

    @Test
    fun exercise3dot18() {
        assertEquals(List.of(2, 4, 6, 8, 10), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).filter { it % 2 == 0 })
    }

    @Test
    fun exercise3dot19() {
        assertEquals(List.of(1, 1, 2, 2, 3, 3), List.of(1, 2, 3).flatMap { i -> List.of(i, i) })
        assertEquals(List.of(1, 1, 2, 2, 3, 3), List.of(1, 2, 3).flatMapViaFoldRight { i -> List.of(i, i) })
    }

    @Test
    fun exercise3dot20() {
        assertEquals(List.of(2, 4, 6, 8, 10), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).filterViaFlatMap { it % 2 == 0 })
    }

    @Test
    fun exercise3dot21() {
        assertEquals(List.of(5, 7, 9), List.addElements(List.of(1, 2, 3), List.of(4, 5, 6)))
    }

    @Test
    fun exercise3dot22() {
        assertEquals(
            List.of("m", "ii", "kkk", "eeee"),
            List.zipWith(List.of("m", "i", "k", "e"), List.of(1, 2, 3, 4)) { a, b -> a.repeat(b) }
        )
    }

    @Test
    fun exercise3dot23() {
        val xs = List.of(1, 2, 3, 4, 5)

        assertTrue(xs.startsWith(List.empty()))
        assertTrue(xs.startsWith(List.of(1)))
        assertTrue(xs.startsWith(List.of(1, 2)))
        assertTrue(xs.startsWith(List.of(1, 2, 3)))
        assertTrue(xs.startsWith(List.of(1, 2, 3, 4)))
        assertTrue(xs.startsWith(List.of(1, 2, 3, 4, 5)))
        assertFalse(xs.startsWith(List.of(1, 2, 3, 4, 5, 6)))
        assertFalse(xs.startsWith(List.of(2)))
        assertFalse(xs.startsWith(List.of(1, 1)))
        assertFalse(xs.startsWith(List.of(1, 3)))

        assertTrue(xs.hasSubsequence(List.empty()))
        assertTrue(xs.hasSubsequence(List.of(1)))
        assertTrue(xs.hasSubsequence(List.of(1, 2)))
        assertTrue(xs.hasSubsequence(List.of(1, 2, 3)))
        assertTrue(xs.hasSubsequence(List.of(1, 2, 3, 4)))
        assertTrue(xs.hasSubsequence(List.of(1, 2, 3, 4, 5)))
        assertFalse(xs.hasSubsequence(List.of(1, 2, 3, 4, 5, 6)))
        assertFalse(xs.hasSubsequence(List.of(6)))
        assertFalse(xs.hasSubsequence(List.of(1, 3)))
        assertFalse(xs.hasSubsequence(List.of(1, 2, 4)))
        assertTrue(xs.hasSubsequence(List.of(2)))
        assertTrue(xs.hasSubsequence(List.of(2, 3)))
        assertTrue(xs.hasSubsequence(List.of(2, 3, 4)))
        assertTrue(xs.hasSubsequence(List.of(2, 3, 4, 5)))
        assertFalse(xs.hasSubsequence(List.of(2, 3, 4, 5, 6)))
        assertTrue(xs.hasSubsequence(List.of(3)))
        assertTrue(xs.hasSubsequence(List.of(3, 4)))
        assertTrue(xs.hasSubsequence(List.of(3, 4, 5)))
        assertFalse(xs.hasSubsequence(List.of(3, 4, 5, 6)))
        assertTrue(xs.hasSubsequence(List.of(4)))
        assertTrue(xs.hasSubsequence(List.of(4, 5)))
        assertFalse(xs.hasSubsequence(List.of(4, 5, 6)))
        assertTrue(xs.hasSubsequence(List.of(5)))
        assertFalse(xs.hasSubsequence(List.of(5, 6)))
    }

    @Test
    fun exercise3dot24() {
        assertEquals(1, Leaf(1).size())
        assertEquals(3, Branch(Leaf(1), Leaf(2)).size())
        assertEquals(5, Branch(Leaf(1), Branch(Leaf(2), Leaf(3))).size())
        assertEquals(7, Branch(Branch(Leaf(1), Leaf(2)), Branch(Leaf(3), Leaf(4))).size())
    }

    @Test
    fun exercise3dot25() {
        assertEquals(8, Branch(Branch(Leaf(8), Leaf(2)), Branch(Leaf(3), Leaf(7))).maximum())
        assertEquals(10, Branch(Branch(Leaf(8), Leaf(10)), Branch(Leaf(9), Leaf(2))).maximum())
        assertEquals(7, Branch(Branch(Leaf(3), Leaf(7)), Branch(Leaf(7), Leaf(4))).maximum())
        assertEquals(6, Branch(Branch(Leaf(3), Leaf(2)), Branch(Leaf(6), Leaf(4))).maximum())
        assertEquals(
            6, Branch(
                Branch(
                    Leaf(3),
                    Branch(
                        Leaf(2),
                        Leaf(1)
                    )
                ),
                Branch(
                    Branch(
                        Leaf(6),
                        Leaf(5)
                    ),
                    Leaf(4)
                )
            ).maximum()
        )
    }

    @Test
    fun exercise3dot26() {
        val l = Leaf(1)
        val b = Branch(l, l)
        assertEquals(0, l.depth())
        assertEquals(1, b.depth())
        assertEquals(2, Branch(l, b).depth())
        assertEquals(3, Branch(l, Branch(b, l)).depth())
        assertEquals(4, Branch(l, Branch(Branch(l, b), l)).depth())
        assertEquals(5, Branch(l, Branch(Branch(l, Branch(l, b)), l)).depth())
        assertEquals(6, Branch(l, Branch(Branch(l, Branch(Branch(b, l), b)), l)).depth())
    }

    @Test
    fun exercise3dot27() {
        assertEquals(
            Branch(Branch(Leaf(4), Leaf(9)), Branch(Leaf(16), Leaf(25))),
            Branch(Branch(Leaf(2), Leaf(3)), Branch(Leaf(4), Leaf(5))).map { it * it }
        )
        assertEquals(
            Branch(Branch(Leaf("xx"), Leaf("xxx")), Branch(Leaf("xxxx"), Leaf("xxxxx"))),
            Branch(Branch(Leaf(2), Leaf(3)), Branch(Leaf(4), Leaf(5))).map { "x".repeat(it) }
        )
    }
}
