package chapter4

import kotlin.math.pow

sealed class Option<out A>

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
