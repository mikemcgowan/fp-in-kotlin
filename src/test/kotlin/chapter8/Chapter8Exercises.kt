package chapter8

import chapter3.foldLeft
import chapter3.length
import chapter3.max
import chapter3.sorted
import chapter6.RNG
import chapter6.SimpleRNG
import chapter8.Prop.Companion.forAll
import kotlin.test.Test
import kotlin.test.assertTrue

internal class Chapter8Exercises {

    private fun run(p: Prop, maxSize: MaxSize = 100, testCases: TestCases = 100, rng: RNG = SimpleRNG(System.currentTimeMillis())) {
        val result = p.check(maxSize, testCases, rng)
        assertTrue(
            result is Passed || result is Proved,
            if (result is Falsified) "Falsified after ${result.successes} passed tests: ${result.failure}" else null
        )
    }

    @Test
    fun exercise8dot13() {
        val smallInt = Gen.choose(-10, 10)
        val maxProp = forAll(smallInt.nonEmptyListOf()) { ns ->
            val mx = ns.max() ?: throw IllegalStateException("max on empty list")
            !ns.exists { it > mx }
        }
        run(maxProp)
    }

    @Test
    fun exercise8dot14() {
        val smallInt = Gen.choose(-10, 10)
        val sortedProp = forAll(smallInt.nonEmptyListOf()) { ns ->
            val sorted = ns.sorted()
            val initial: Int? = null
            if (sorted.length() < 1) true
            else sorted.foldLeft(Pair(initial, true)) { acc, n ->
                if (acc.second) Pair(n, acc.first == null || n >= acc.first!!)
                else acc
            }.second
        }
        run(sortedProp)
    }
}
