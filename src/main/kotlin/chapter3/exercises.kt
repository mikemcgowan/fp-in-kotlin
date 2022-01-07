package chapter3

fun main() {
    exercise3dot1()
    exercise3dot2()
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
