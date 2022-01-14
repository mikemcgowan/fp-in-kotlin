package chapter3

fun main() {
    exercise3dot1()
    exercise3dot2()
    exercise3dot3()
    exercise3dot4()
    exercise3dot5()
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
