package chapter7

fun <A> List<A>.splitAt(n: Int): Pair<List<A>, List<A>> = take(n) to drop(n)

val <T> List<T>.head: T get() = first()
val <T> List<T>.tail: List<T> get() = drop(1)
val Nil = listOf<Nothing>()

class Par<A>(val get: A) {
    companion object {
        fun <A> unit(a: A): Par<A> = Par(a)

        fun <A> lazyUnit(a: () -> A): Par<A> = Par(a())

        fun <A, B, C> map2(p1: Par<A>, p2: Par<B>, f: (A, B) -> C): Par<C> = Par(f(p1.get, p2.get))

        fun <A, B> map(p: Par<A>, f: (A) -> B): Par<B> = map2(p, unit(Unit)) { a, _ -> f(a) }

        fun <A> fork(p: () -> Par<A>): Par<A> = p()

        fun <A> run(p: Par<A>): A = p.get

        fun <A, B> asyncF(f: (A) -> B): (A) -> Par<B> = { a -> lazyUnit { f(a) } }

        fun <A> sequence(ps: List<Par<A>>): Par<List<A>> =
            when {
                ps.isEmpty() -> unit(Nil)
                ps.size == 1 -> map(ps.head) { listOf(it) }
                else -> {
                    val (l, r) = ps.splitAt(ps.size / 2)
                    map2(sequence(l), sequence(r)) { la, lb -> la + lb }
                }
            }

        fun <A, B> parMap(ps: List<A>, f: (A) -> B): Par<List<B>> =
            fork {
                val fbs = ps.map(asyncF(f))
                sequence(fbs)
            }

        fun <A> parFilter(ps: List<A>, f: (A) -> Boolean): Par<List<A>> {
            val pars = ps.map { lazyUnit { it } }
            return map(sequence(pars)) { it.flatMap { a -> if (f(a)) listOf(a) else emptyList() } }
        }
    }
}
