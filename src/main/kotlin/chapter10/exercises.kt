package chapter10

import arrow.core.None
import arrow.core.Option
import arrow.core.orElse

interface Monoid<A> {
    fun combine(a1: A, a2: A): A
    val nil: A
}

fun intAddition(): Monoid<Int> = object : Monoid<Int> {
    override fun combine(a1: Int, a2: Int): Int = a1 + a2
    override val nil: Int = 0
}

fun intMultiplication(): Monoid<Int> = object : Monoid<Int> {
    override fun combine(a1: Int, a2: Int): Int = a1 * a2
    override val nil: Int = 1
}

fun booleanOr(): Monoid<Boolean> = object : Monoid<Boolean> {
    override fun combine(a1: Boolean, a2: Boolean): Boolean = a1 || a2
    override val nil: Boolean = false
}

fun booleanAnd(): Monoid<Boolean> = object : Monoid<Boolean> {
    override fun combine(a1: Boolean, a2: Boolean): Boolean = a1 && a2
    override val nil: Boolean = true
}

fun <A> optionMonoid(): Monoid<Option<A>> = object : Monoid<Option<A>> {
    override fun combine(a1: Option<A>, a2: Option<A>): Option<A> = a1.orElse { a2 }
    override val nil: Option<A> = None
}

fun <A> endoMonoid(): Monoid<(A) -> A> = object : Monoid<(A) -> A> {
    override fun combine(a1: (A) -> A, a2: (A) -> A): (A) -> A = { a1(a2(it)) }
    override val nil: (A) -> A = { it }
}
