package chapter2

fun main() {
    exercise2dot1()
    exercise2dot2()
    exercise2dot3()
    exercise2dot4()
    exercise2dot5()
}

fun exercise2dot1() {
    val expected = listOf(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
    val actual = (1..10).map { fib(it) }
    assert(expected == actual) { "$actual is not $expected" }
}

fun exercise2dot2() {
    assert(isSorted(listOf(1, 2, 3)) { a, b -> b > a })
    assert(!isSorted(listOf(2, 1, 3)) { a, b -> b > a })
}

fun exercise2dot3() {
    val f = curry { x: Int, y: Int -> x + y }
    val g = f(3)
    assert(g(2) == 5)
    assert(g(3) == 6)
    assert(g(7) == 10)
}

fun exercise2dot4() {
    val f = uncurry { x: Int -> { y: Int -> x + y } }
    assert(f(2, 3) == 5)
    assert(f(3, 8) == 11)
}

fun exercise2dot5() {
    val g = { x: Int -> x.toString() }
    val f = { s: String -> s.repeat(5) }
    val h = compose(f, g)
    assert(h(3) == "33333")
}

fun fib(i: Int): Int {
    tailrec fun go(n: Int, current: Int, next: Int): Int =
        if (n == 1) current else go(n - 1, next, current + next)
    return go(i, 0, 1)
}

val <T> List<T>.tail: List<T> get() = drop(1)
val <T> List<T>.head: T get() = first()

fun <A> isSorted(aa: List<A>, order: (A, A) -> Boolean): Boolean {
    tailrec fun go(x: A, xs: List<A>): Boolean =
        when {
            xs.isEmpty() -> true
            !order(x, xs.head) -> false
            else -> go(xs.head, xs.tail)
        }
    return go(aa.head, aa.tail)
}

fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> (C) =
    { a -> { b -> f(a, b) } }

fun <A, B, C> uncurry(f: (A) -> (B) -> (C)): (A, B) -> C =
    { a, b -> f(a)(b) }

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C =
    { a -> f(g(a)) }
