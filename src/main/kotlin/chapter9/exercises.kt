package chapter9

import arrow.core.Either

interface Parser<A>

interface Parsers<PE> {
    fun char(c: Char): Parser<Char>
    fun string(s: String): Parser<String>
    fun <A> run(p: Parser<A>, input: String): Either<PE, A>
    fun <A> listOfN(n: Int, p: Parser<A>): Parser<List<A>>
    fun <A> slice(p: Parser<A>): Parser<String>
    fun <A> succeed(a: A): Parser<A> = string("").map { a }

    infix fun <A> Parser<A>.or(other: Parser<A>): Parser<A>
    fun <A> Parser<A>.many(): Parser<List<A>>
    fun <A> Parser<A>.many1(): Parser<List<A>>
    fun <A, B> Parser<A>.map(f: (A) -> B): Parser<B>
    fun <A, B, C> map2(pa: Parser<A>, pb: Parser<B>, f: (A, B) -> C): Parser<C>
    infix fun <A, B> Parser<A>.product(p: Parser<B>): Parser<Pair<A, B>>
}

object ParseError

abstract class Foo : Parsers<ParseError> {

    override fun <A, B, C> map2(pa: Parser<A>, pb: Parser<B>, f: (A, B) -> C): Parser<C> =
        pa.product(pb).map { (a, b) -> f(a, b) }

    override fun <A> Parser<A>.many1(): Parser<List<A>> =
        map2(this, many()) { a, b -> listOf(a) + b }

    override fun <A> Parser<A>.many(): Parser<List<A>> =
        map2(this, many()) { a, b -> listOf(a) + b } or succeed(emptyList())

    override fun <A> listOfN(n: Int, p: Parser<A>): Parser<List<A>> =
        when {
            n > 0 -> map2(p, listOfN(n - 1, p)) { a, b -> listOf(a) + b }
            else -> succeed(emptyList())
        }
}
