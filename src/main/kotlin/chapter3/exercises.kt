package chapter3

fun main() {
    exercise3dot1()
    exercise3dot2()
    exercise3dot3()
    exercise3dot4()
    exercise3dot5()
    exercise3dot8()
    exercise3dot9()
    exercise3dot10()
}

sealed class List<out A> {
    companion object {
        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }
    }
}

object Nil : List<Nothing>()
data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()

fun <A> List<A>.tail(): List<A> =
    when (this) {
        is Nil -> Nil
        is Cons -> tail
    }

tailrec fun <A> List<A>.drop(n: Int): List<A> =
    when (this) {
        is Nil -> Nil
        is Cons -> if (n == 0) this else tail.drop(n - 1)
    }

tailrec fun <A> List<A>.dropWhile(f: (A) -> Boolean): List<A> =
    when (this) {
        is Nil -> Nil
        is Cons -> if (f(head)) tail.dropWhile(f) else this
    }

fun <A> List<A>.init(): List<A> =
    when (this) {
        is Nil -> Nil
        is Cons -> if (tail is Nil) Nil else Cons(head, tail.init())
    }

fun <A> List<A>.setHead(a: A): List<A> =
    when (this) {
        is Nil -> Nil
        is Cons -> Cons(a, tail)
    }

fun <A, B> List<A>.foldRight(z: B, f: (A, B) -> B): B =
    when (this) {
        is Nil -> z
        is Cons -> f(head, tail.foldRight(z, f))
    }

tailrec fun <A, B> List<A>.foldLeft(z: B, f: (B, A) -> B): B =
    when (this) {
        is Nil -> z
        is Cons -> tail.foldLeft(f(z, head), f)
    }

fun List<Int>.sum(): Int =
    foldLeft(0) { x, y -> x + y }

fun List<Int>.product(): Int =
    foldLeft(1) { x, y -> x * y }

fun <A> List<A>.length(): Int =
    foldRight(0) { _, y -> 1 + y }

fun <A> List<A>.lengthViaFoldLeft(): Int =
    foldLeft(0) { x, _ -> 1 + x }

fun exercise3dot1() {
    val xs = List.of(1, 2, 3, 4)
    assert(xs.tail() == List.of(2, 3, 4))
    assert(xs.tail().tail() == List.of(3, 4))
    assert(xs.tail().tail().tail() == List.of(4))
    assert(xs.tail().tail().tail().tail() == Nil)
}

fun exercise3dot2() {
    val xs = List.of(1, 2, 3, 4)
    assert(xs.setHead(9) == List.of(9, 2, 3, 4))
    assert(List.of(1).setHead(9) == List.of(9))
    assert(Nil.setHead(9) == Nil)
}

fun exercise3dot3() {
    val xs = List.of(1, 2, 3, 4)
    assert(xs.drop(1) == List.of(2, 3, 4))
    assert(xs.drop(2) == List.of(3, 4))
    assert(xs.drop(3) == List.of(4))
    assert(xs.drop(4) == Nil)
}

fun exercise3dot4() {
    val xs = List.of(1, 2, 3, 4, 5, 6, 7, 8)
    assert(xs.dropWhile { it < 0 } == List.of(1, 2, 3, 4, 5, 6, 7, 8))
    assert(xs.dropWhile { it < 5 } == List.of(5, 6, 7, 8))
    assert(xs.dropWhile { it < 8 } == List.of(8))
    assert(xs.dropWhile { it < 9 } == Nil)
}

fun exercise3dot5() {
    assert(List.of(1, 2, 3, 4).init() == List.of(1, 2, 3))
    assert(List.of(1, 2, 3).init() == List.of(1, 2))
    assert(List.of(1, 2).init() == List.of(1))
    assert(List.of(1).init() == Nil)
}

fun exercise3dot8() {
    assert(List.of(1, 2, 3, 4).foldRight(0) { x, y -> x + y } == 10)
    assert(List.of(1, 2, 3, 4).foldRight(1) { x, y -> x * y } == 24)
    assert(List.of(1, 2, 3).length() == 3)
    assert(List.of(1, 2).length() == 2)
    assert(List.of(1).length() == 1)
    assert(Nil.length() == 0)

    val expand = arrayOf(
        List.of(1, 2, 3).length(),
        List.of(1, 2, 3).foldRight(0) { _, y -> 1 + y },
        1 + List.of(2, 3).foldRight(0) { _, y -> 1 + y },
        1 + 1 + List.of(3).foldRight(0) { _, y -> 1 + y },
        1 + 1 + 1 + Nil.foldRight(0) { _, y -> 1 + y },
        1 + 1 + 1 + 0,
    )
    assert(expand.all { it == 3 })
}

fun exercise3dot9() {
    assert(List.of(1, 2, 3, 4).foldLeft(0) { x, y -> x + y } == 10)
    assert(List.of(1, 2, 3, 4).foldLeft(1) { x, y -> x * y } == 24)

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
    assert(expand.all { it == 10 })
}

fun exercise3dot10() {
    assert(Nil.sum() == 0)
    assert(List.of(1, 2, 3, 4).sum() == 10)
    assert(List.of(1, 2, 3, 4, 5).sum() == 15)

    assert(Nil.product() == 1)
    assert(List.of(2, 3).product() == 6)
    assert(List.of(2, 3, 4).product() == 24)

    assert(Nil.lengthViaFoldLeft() == 0)
    assert(List.of('m', 'i', 'k', 'e').lengthViaFoldLeft() == 4)
    assert(List.of(true, false, true, false, true).lengthViaFoldLeft() == 5)
}
