package chapter8

import arrow.core.Either
import chapter3.List
import chapter6.RNG
import chapter6.State
import chapter6.double

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

        fun <A> listOfN(n: Int, ga: Gen<A>): Gen<List<A>> {
            val kotlinList = kotlin.collections.List(n) { ga.sample }
            return Gen(State.sequence(List.of(*kotlinList.toTypedArray())))
        }
    }
}
