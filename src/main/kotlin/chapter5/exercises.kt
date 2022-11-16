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

        fun ones(): Stream<Int> = cons({ 1 }, { ones() })
        fun <A> constant(a: A): Stream<A> = cons({ a }, { constant(a) })
        fun onesViaConstant(): Stream<Int> = constant(1)

        fun from(n: Int): Stream<Int> = cons({ n }, { from(n + 1) })
    }
}

data class Cons<out A>(val head: () -> A, val tail: () -> Stream<A>) : Stream<A>()
object Empty : Stream<Nothing>()

fun <A> Stream<A>.headOption(): Option<A> =
    when (this) {
        is Empty -> None
        is Cons -> Some(head())
    }

fun <A> Stream<A>.headOptionViaFoldRight(): Option<A> =
    foldRight({ Option.empty() }) { a, _ -> Some(a) }

fun <A> Stream<A>.toList(): List<A> {
    tailrec fun go(s: Stream<A>, acc: List<A>): List<A> =
        when (s) {
            is Empty -> acc.reverse()
            is Cons -> go(s.tail(), chapter3.Cons(s.head(), acc))
        }
    return go(this, List.empty())
}

fun <A> Stream<A>.take(n: Int): Stream<A> {
    fun go(s: Stream<A>, m: Int): Stream<A> =
        if (m == 0) Stream.empty() else when (s) {
            is Empty -> Stream.empty()
            is Cons -> Stream.cons(s.head) { go(s.tail(), m - 1) }
        }
    return go(this, n)
}

fun <A> Stream<A>.drop(n: Int): Stream<A> {
    tailrec fun go(s: Stream<A>, m: Int): Stream<A> =
        if (m == 0) s else when (s) {
            is Empty -> Stream.empty()
            is Cons -> go(s.tail(), m - 1)
        }
    return go(this, n)
}

fun <A> Stream<A>.takeWhile(p: (A) -> Boolean): Stream<A> =
    when (this) {
        is Empty -> Stream.empty()
        is Cons -> if (p(head())) Stream.cons(head) { tail().takeWhile(p) } else Stream.empty()
    }

fun <A, B> Stream<A>.foldRight(z: () -> B, f: (A, () -> B) -> B): B =
    when (this) {
        is Cons -> f(head()) { tail().foldRight(z, f) }
        is Empty -> z()
    }

fun <A> Stream<A>.forAll(p: (A) -> Boolean): Boolean =
    when (this) {
        is Empty -> true
        is Cons -> p(head()) && tail().forAll(p)
    }

fun <A> Stream<A>.forAllViaFoldRight(p: (A) -> Boolean): Boolean =
    foldRight({ true }) { a, b -> p(a) && b() }

fun <A> Stream<A>.takeWhileViaFoldRight(p: (A) -> Boolean): Stream<A> =
    foldRight({ Stream.empty() }) { a, b -> if (p(a)) Stream.cons({ a }, b) else b() }

fun <A, B> Stream<A>.map(f: (A) -> B): Stream<B> =
    foldRight({ Stream.empty() }) { a, b -> Stream.cons({ f(a) }, b) }

fun <A> Stream<A>.filter(p: (A) -> Boolean): Stream<A> =
    foldRight({ Stream.empty() }) { a, b -> if (p(a)) Stream.cons({ a }, b) else b() }

fun <A> Stream<A>.append(other: () -> Stream<A>): Stream<A> =
    foldRight(other) { a, b -> Stream.cons({ a }, b) }

fun <A, B> Stream<A>.flatMap(f: (A) -> Stream<B>): Stream<B> =
    foldRight({ Stream.empty() }) { a, b -> f(a).append(b) }
