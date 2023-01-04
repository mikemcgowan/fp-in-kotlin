package chapter8

import arrow.core.Either
import chapter3.List
import chapter6.RNG
import chapter6.State
import chapter6.double
import kotlin.math.absoluteValue

typealias SuccessCount = Int
typealias FailedCase = String

interface Prop {
    fun check(): Either<Pair<FailedCase, SuccessCount>, SuccessCount>
    fun and(p: Prop): Prop
}

data class Gen<A>(val sample: State<RNG, A>) {
    companion object {
        fun choose(start: Int, stopExclusive: Int): Gen<Int> =
            Gen(State { rng: RNG -> double(rng) }
                .map { start + (it * (stopExclusive - start)) }
                .map { it.toInt() })

        fun <A> unit(a: A): Gen<A> =
            Gen(State.unit(a))

        fun boolean(): Gen<Boolean> =
            Gen(State { rng: RNG -> double(rng) }.map { it >= 0.5 })

        fun double(): Gen<Double> =
            Gen(State { rng: RNG -> double(rng) })

        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> {
            val kotlinList = kotlin.collections.List(n) { ga.sample }
            return Gen(State.sequence(List.of(*kotlinList.toTypedArray())))
        }

        fun <A> listOfN(gn: Gen<Int>, ga: Gen<A>): Gen<List<A>> =
            gn.flatMap { n -> listOfN(n, ga) }

        fun <A> union(ga: Gen<A>, gb: Gen<A>): Gen<A> =
            boolean().flatMap { if (it) ga else gb }

        fun <A> weighted(pga: Pair<Gen<A>, Double>, pgb: Pair<Gen<A>, Double>): Gen<A> {
            val wa = pga.second.absoluteValue
            val wb = pgb.second.absoluteValue
            val pa = wa / (wa + wb)
            return double().flatMap { if (it < pa) pga.first else pgb.first }
        }
    }

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })
}
