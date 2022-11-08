package chapter4

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

internal class Chapter4Exercises {

    @Test
    fun exercise4dot4() {
        var xs: List<Option<Int>>
        var ys: Option<List<Int>>

        xs = listOf(Some(1), Some(2), Some(3), Some(4))
        ys = Option.sequence(xs)
        assertEquals(Some(listOf(1, 2, 3, 4)), ys)

        xs = listOf(Some(1), Some(2), None, Some(4))
        ys = Option.sequence(xs)
        assertEquals(None, ys)

        xs = listOf(None, None, None)
        ys = Option.sequence(xs)
        assertEquals(None, ys)
    }

    @Test
    fun exercise4dot5() {
        assertEquals(Some(listOf(1, 4, 9, 16)), Option.traverseInefficient(listOf(1, 2, 3, 4)) { x -> Some(x * x) })
        assertEquals(Some(listOf(1, 2, 3)), Option.traverseInefficient(listOf("1", "2", "3")) { s -> Option.catches { s.toInt() } })
        assertEquals(None, Option.traverseInefficient(listOf("1", "x", "3")) { s -> Option.catches { s.toInt() } })

        assertEquals(Some(listOf(1, 4, 9, 16)), Option.traverse(listOf(1, 2, 3, 4)) { x -> Some(x * x) })
        assertEquals(Some(listOf(1, 2, 3)), Option.traverse(listOf("1", "2", "3")) { s -> Option.catches { s.toInt() } })
        assertEquals(None, Option.traverse(listOf("1", "x", "3")) { s -> Option.catches { s.toInt() } })

        assertEquals(Some(listOf(1, 2, 3, 4)), Option.sequenceViaTraverse(listOf(Some(1), Some(2), Some(3), Some(4))))
    }

    @Test
    fun exercise4dot6() {
        val greet: (String) -> String = { "Hi, $it" }
        assertEquals(Right("Hi, Mike"), Right("Mike").map(greet))
        assertEquals(Left("Oops"), Left("Oops").map(greet))

        val square: (Int) -> Either<Int, Int> = { Right(it * it) }
        assertEquals(Right(16), Right(4).flatMap(square))
        assertEquals(Left("Oops"), Left("Oops").flatMap(square))

        assertEquals(Right(7), Right(7).orElse { Right(9) })
        assertEquals(Right(7), Left("Oops").orElse { Right(7) })

        val multi: (Int, Int) -> Int = { a, b -> a * b }
        assertEquals(Right(12), map2(Right(3), Right(4), multi))
        assertEquals(Left("Oops"), map2(Left("Oops"), Right(4), multi))
        assertEquals(Left("Oops"), map2(Right(4), Left("Oops"), multi))
        assertEquals(Left("Oops"), map2(Left("Oops"), Left("Dear"), multi))
    }

    @Test
    fun exercise4dot7() {
        assertEquals(Right(listOf(1, 4, 9, 16)), Either.traverse(listOf(1, 2, 3, 4)) { x -> Right(x * x) })
        val traverseResult = Either.traverse(listOf("1", "x", "3", "y", "5")) { s -> Either.catches { s.toInt() } }
        if (traverseResult is Left) assertEquals("""For input string: "x"""", traverseResult.value.message) else fail()

        assertEquals(Right(listOf(1, 2, 3)), Either.sequenceViaTraverse(listOf(Right(1), Right(2), Right(3))))
        val sequenceResult = Either.sequenceViaTraverse(listOf(Right(1), Left("Oops"), Right(3)))
        if (sequenceResult is Left) assertEquals("Oops", sequenceResult.value) else fail()
    }
}
