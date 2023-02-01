package chapter10

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Chapter10Exercises {

    private val words = listOf("Hic", "Est", "Index")

    @Test
    fun stringMonoid() {
        assertEquals("HicEstIndex", words.foldRight(stringMonoid.nil, stringMonoid::combine))
        assertEquals("HicEstIndex", words.fold(stringMonoid.nil, stringMonoid::combine))
    }

    @Test
    fun concatenate() {
        assertEquals("HicEstIndex", concatenate(words, stringMonoid))
    }

    @Test
    fun foldMap() {
        assertEquals("Hello", foldMap(listOf('H', 'e', 'l', 'l', 'o'), stringMonoid) { it.toString() })
    }

    @Test
    fun foldRightViaFoldMap() {
        assertEquals("HicEstIndex", foldRightViaFoldMap(words, "") { a, b -> a + b })
    }

    @Test
    fun foldLeftViaFoldMap() {
        assertEquals("HicEstIndex", foldLeftViaFoldMap(words, "") { a, b -> a + b })
    }
}
