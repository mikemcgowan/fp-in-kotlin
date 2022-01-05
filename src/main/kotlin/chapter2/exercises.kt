package chapter2

fun main() {
    // exercise 2.1 on page 20
    (1..20).forEach { println(fib(it)) }
}

fun fib(i: Int): Int {
    tailrec fun go(n: Int, current: Int, next: Int): Int =
        if (n == 1) current else go(n - 1, next, current + next)
    return go(i, 0, 1)
}
