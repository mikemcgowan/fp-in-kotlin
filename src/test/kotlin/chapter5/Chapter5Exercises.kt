package chapter5

import chapter3.List
import chapter4.None
import chapter4.Some
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

    @Test
    fun exercise5dot6() {
        assertEquals(Some(1), xs.headOption());
        assertEquals(Some(1), xs.headOptionViaFoldRight());

        val ys: Stream<Int> = Stream.empty()
        assertEquals(None, ys.headOption());
        assertEquals(None, ys.headOptionViaFoldRight());
    }

    @Test
    fun exercise5dot7() {
        assertEquals(List.of(1, 4, 9, 16, 25), xs.map { it * it }.toList())
        assertEquals(List.of(2, 4), xs.filter { it % 2 == 0 }.toList())

        val ys: Stream<Int> = Stream.of(6, 7, 8, 9)
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), xs.append { ys }.toList())

        val zs: Stream<Int> = ys.flatMap { i -> Stream.of(i, i) }
        assertEquals(List.of(6, 6, 7, 7, 8, 8, 9, 9), zs.toList())
    }

    @Test
    fun exercise5dot8() {
        assertEquals(List.of(1, 1, 1), Stream.ones().take(3).toList())
        assertEquals(List.of(1, 1, 1), Stream.onesViaConstant().take(3).toList())
        assertEquals(List.of(1, 1, 1), Stream.constant(1).take(3).toList())
        assertEquals(List.of("hi", "hi", "hi"), Stream.constant("hi").take(3).toList())
    }

    @Test
    fun exercise5dot9() {
        assertEquals(List.of(10, 11, 12, 13, 14), Stream.from(10).take(5).toList())
    }

    @Test
    fun exercise5dot10() {
        assertEquals(List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144), Stream.fibs().take(13).toList())
    }
}
