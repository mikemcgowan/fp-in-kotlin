package chapter5

import chapter3.List
import chapter3.reverse
import chapter4.None
import chapter4.Option
import chapter4.Some

sealed class Stream<out A> {
    companion object {
        fun <A> cons(hd: () -> A, tl: () -> Stream<A>): Stream<A> {
            val head: A by lazy(hd)
            val tail: Stream<A> by lazy(tl)
            return Cons({ head }, { tail })
        }

        fun <A> empty(): Stream<A> = Empty

        fun <A> of(vararg xs: A): Stream<A> =
            if (xs.isEmpty()) empty() else cons({ xs[0] }, { of(*xs.sliceArray(1 until xs.size)) })
    }
}

data class Cons<out A>(val head: () -> A, val tail: () -> Stream<A>) : Stream<A>()
object Empty : Stream<Nothing>()

fun <A> Stream<A>.headOption(): Option<A> =
    when (this) {
        is Empty -> None
        is Cons -> Some(head())
    }

fun <A> Stream<A>.toList(): List<A> {
    tailrec fun go(s: Stream<A>, acc: List<A>): List<A> =
        when (s) {
            is Empty -> acc.reverse()
            is Cons -> go(s.tail(), chapter3.Cons(s.head(), acc))
        }
    return go(this, List.empty())
}
