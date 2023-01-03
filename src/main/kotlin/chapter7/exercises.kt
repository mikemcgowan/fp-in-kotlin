package chapter7

fun <A> List<A>.splitAt(n: Int): Pair<List<A>, List<A>> = take(n) to drop(n)

class Par<A>(val get: A) {
    companion object {
        fun <A> unit(a: A): Par<A> = Par(a)

        fun <A> lazyUnit(a: () -> A): Par<A> = Par(a())

        fun <A, B, C> map2(p1: Par<A>, p2: Par<B>, f: (A, B) -> C): Par<C> = Par(f(p1.get, p2.get))

        fun <A> fork(p: () -> Par<A>): Par<A> = p()

        fun <A> run(p: Par<A>): A = p.get

        fun <A, B> asyncF(f: (A) -> B): (A) -> Par<B> = { a -> lazyUnit { f(a) } }
    }
}
