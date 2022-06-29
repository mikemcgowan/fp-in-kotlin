package chapter3

sealed class List<out A> {
    companion object {
        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun <A> empty(): List<A> = Nil

        fun <A> concat(xs: List<List<A>>): List<A> =
            xs.foldRight(empty()) { ys, acc -> ys.foldRight(acc) { a, acc2 -> Cons(a, acc2) } }

        fun <A> concatViaAppend(xs: List<List<A>>): List<A> =
            xs.foldLeft(empty()) { acc, ys -> acc.append(ys) }
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

fun <A> List<A>.reverse(): List<A> =
    foldLeft(List.empty()) { acc, a -> Cons(a, acc) }

fun <A> List<A>.append(other: List<A>): List<A> =
    foldRight(other) { a, acc -> Cons(a, acc) }
