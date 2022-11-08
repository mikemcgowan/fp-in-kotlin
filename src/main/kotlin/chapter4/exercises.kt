package chapter4

import kotlin.math.pow

// Option

sealed class Option<out A> {
    companion object {
        fun <A> sequence(xs: List<Option<A>>): Option<List<A>> =
            xs.foldRight(Some(emptyList())) { oa, oacc ->
                map2(oa, oacc) { a, acc -> listOf(a).plus(acc) }
            }

        fun <A, B> traverseInefficient(xs: List<A>, f: (A) -> Option<B>): Option<List<B>> =
            sequence(xs.map(f))

        fun <A, B> traverse(xs: List<A>, f: (A) -> Option<B>): Option<List<B>> =
            xs.foldRight(Some(emptyList())) { a, oacc ->
                map2(f(a), oacc) { aa, acc -> listOf(aa).plus(acc) }
            }

        fun <A> sequenceViaTraverse(xs: List<Option<A>>): Option<List<A>> =
            traverse(xs) { it }
    }
}

object None : Option<Nothing>()
data class Some<out A>(val get: A) : Option<A>()

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> =
    when (this) {
        is None -> None
        is Some -> Some(f(get))
    }

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> =
    when (this) {
        is None -> None
        is Some -> f(get)
    }

fun <A, B> Option<A>.getOrElse(default: () -> A): A =
    when (this) {
        is None -> default()
        is Some -> get
    }

fun <A, B> Option<A>.orElse(ob: () -> Option<A>): Option<A> =
    when (this) {
        is None -> ob()
        is Some -> this
    }

fun <A, B> Option<A>.filter(f: (A) -> Boolean): Option<A> =
    when (this) {
        is None -> None
        is Some -> if (f(get)) this else None
    }

fun mean(xs: List<Double>): Option<Double> =
    if (xs.isEmpty()) None else Some(xs.sum() / xs.size)

fun variance(xs: List<Double>): Option<Double> =
    mean(xs).flatMap { m -> mean(xs.map { (it - m).pow(2) }) }

fun <A, B, C> map2(oa: Option<A>, ob: Option<B>, f: (A, B) -> C): Option<C> =
    oa.flatMap { a -> ob.map { b -> f(a, b) } }

fun <A> catches(a: () -> A): Option<A> =
    try {
        Some(a())
    } catch (e: Throwable) {
        None
    }

// Either

sealed class Either<out E, out A>

data class Left<out E>(val value: E) : Either<E, Nothing>()
data class Right<out A>(val value: A) : Either<Nothing, A>()

fun <E, A, B> Either<E, A>.map(f: (A) -> B): Either<E, B> =
    when (this) {
        is Left -> this
        is Right -> Right(f(value))
    }

fun <E, A, B> Either<E, A>.flatMap(f: (A) -> Either<E, B>): Either<E, B> =
    when (this) {
        is Left -> this
        is Right -> f(value)
    }

fun <E, A> Either<E, A>.orElse(f: () -> Either<E, A>): Either<E, A> =
    when (this) {
        is Left -> f()
        is Right -> this
    }

fun <E, A, B, C> map2(ae: Either<E, A>, be: Either<E, B>, f: (A, B) -> C): Either<E, C> =
    ae.flatMap { a -> be.map { b -> f(a, b) } }
