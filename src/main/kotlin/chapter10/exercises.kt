package chapter10

import arrow.core.None
import arrow.core.Option
import arrow.core.orElse
import chapter7.head
import chapter7.splitAt
import kotlin.math.min

interface Monoid<A> {
    fun combine(a1: A, a2: A): A
    val nil: A
}

fun <A> dual(m: Monoid<A>): Monoid<A> = object : Monoid<A> {
    override fun combine(a1: A, a2: A): A = m.combine(a2, a1)
    override val nil: A = m.nil
}

fun <A> concatenate(xs: List<A>, m: Monoid<A>): A =
    xs.fold(m.nil, m::combine)

fun <A, B> foldMap(xs: List<A>, m: Monoid<B>, f: (A) -> B): B =
    xs.fold(m.nil) { acc, a -> m.combine(acc, f(a)) }

fun <A, B> foldMapBalanced(xs: List<A>, m: Monoid<B>, f: (A) -> B): B =
    when (val n = xs.size) {
        0 -> m.nil
        1 -> f(xs.head)
        else -> {
            val (l, r) = xs.splitAt(n / 2)
            m.combine(foldMapBalanced(l, m, f), foldMapBalanced(r, m, f))
        }
    }

fun <A, B> foldRightViaFoldMap(xs: List<A>, z: B, f: (A, B) -> B): B =
    foldMap(xs, endoMonoid()) { a: A -> { b: B -> f(a, b) } }(z)

fun <A, B> foldLeftViaFoldMap(xs: List<A>, z: B, f: (B, A) -> B): B =
    foldMap(xs, dual(endoMonoid())) { a: A -> { b: B -> f(b, a) } }(z)

val stringMonoid = object : Monoid<String> {
    override fun combine(a1: String, a2: String): String = a1 + a2
    override val nil: String = ""
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

sealed class WC

data class Stub(val chars: String) : WC()
data class Part(val l: String, val words: Int, val r: String) : WC()

fun wcMonoid(): Monoid<WC> = object : Monoid<WC> {
    override fun combine(a1: WC, a2: WC): WC =
        when (a1) {
            is Stub -> when (a2) {
                is Stub -> Stub(a1.chars + a2.chars)
                is Part -> Part(a1.chars + a2.l, a2.words, a2.r)
            }

            is Part -> when (a2) {
                is Stub -> Part(a1.l, a1.words, a1.r + a2.chars)
                is Part -> Part(a1.l, a1.words + a2.words + (if ((a1.r + a2.l).isEmpty()) 0 else 1), a2.r)
            }
        }

    override val nil: WC = Stub("")
}

fun wordCount(s: String): Int {
    fun unstub(s: String): Int = min(s.length, 1)
    val wcm = wcMonoid()
    val wc = foldMap(s.toList(), wcm) { if (it.isWhitespace()) Part("", 0, "") else Stub("$it") }
    return when (wc) {
        is Stub -> unstub(wc.chars)
        is Part -> unstub(wc.l) + wc.words + unstub(wc.r)
    }
}
