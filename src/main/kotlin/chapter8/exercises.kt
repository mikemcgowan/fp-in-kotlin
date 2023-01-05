package chapter8

import arrow.core.getOrElse
import arrow.core.toOption
import chapter3.List
import chapter6.RNG
import chapter6.State
import chapter6.double
import kotlin.math.absoluteValue

typealias SuccessCount = Int
typealias TestCases = Int
typealias FailedCase = String

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

    fun <B> map(f: (A) -> B): Gen<B> =
        Gen(sample.map(f))

    fun <B> flatMap(f: (A) -> Gen<B>): Gen<B> =
        Gen(sample.flatMap { a -> f(a).sample })

    fun unsized(): SGen<A> =
        SGen { this }
}

data class SGen<A>(val forSize: (Int) -> Gen<A>) {
    operator fun invoke(i: Int): Gen<A> =
        forSize(i)

    fun <B> map(f: (A) -> B): SGen<B> =
        SGen { forSize(it).map(f) }

    fun <B> flatMap(f: (A) -> Gen<B>): SGen<B> =
        SGen { forSize(it).flatMap(f) }
}

sealed class Result {
    abstract fun isFalsified(): Boolean
}

object Passed : Result() {
    override fun isFalsified(): Boolean = false
}

data class Falsified(val failure: FailedCase, val successes: SuccessCount) : Result() {
    override fun isFalsified(): Boolean = true
}

data class Prop(val check: (TestCases, RNG) -> Result) {

    fun and(p: Prop): Prop =
        Prop { n, rng ->
            when (val prop = check(n, rng)) {
                is Passed -> p.check(n, rng)
                is Falsified -> prop
            }
        }

    fun or(p: Prop): Prop =
        Prop { n, rng ->
            when (val prop = check(n, rng)) {
                is Passed -> prop
                is Falsified -> p.check(n, rng)
            }
        }

    companion object {
        fun <A> forAll(ga: Gen<A>, f: (A) -> Boolean): Prop =
            Prop { n: TestCases, rng: RNG ->
                randomSequence(ga, rng).mapIndexed { i, a ->
                    try {
                        if (f(a)) Passed
                        else Falsified(a.toString(), i)
                    } catch (e: Exception) {
                        Falsified(buildMessage(a, e), i)
                    }
                }.take(n).find { it.isFalsified() }.toOption().getOrElse { Passed }
            }

        private fun <A> randomSequence(ga: Gen<A>, rng: RNG): Sequence<A> =
            sequence {
                val (a: A, rng2: RNG) = ga.sample.run(rng)
                yield(a)
                yieldAll(randomSequence(ga, rng2))
            }

        private fun <A> buildMessage(a: A, e: Exception) =
            """
            |test case: $a
            |generated an exception: ${e.message}
            |stacktrace:
            |${e.stackTrace.joinToString("\n")}
            """.trimMargin()
    }
}
