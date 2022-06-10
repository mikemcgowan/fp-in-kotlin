package chapter2

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
