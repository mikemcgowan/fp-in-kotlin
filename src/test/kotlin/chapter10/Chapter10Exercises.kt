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
    fun foldMapBalanced() {
        assertEquals("Hello", foldMapBalanced(listOf('H', 'e', 'l', 'l', 'o'), stringMonoid) { it.toString() })
    }

    @Test
    fun foldRightViaFoldMap() {
        assertEquals("HicEstIndex", foldRightViaFoldMap(words, "") { a, b -> a + b })
    }

    @Test
    fun foldLeftViaFoldMap() {
        assertEquals("HicEstIndex", foldLeftViaFoldMap(words, "") { a, b -> a + b })
    }

    @Test
    fun wcMonoidLaws() {
        val m = wcMonoid()
        val wc1 = Part("", 2, "bro")
        val wc2 = Part("wn", 4, "la")
        val wc3 = Part("zy", 1, "")
        val wc12 = m.combine(wc1, wc2)
        val wc23 = m.combine(wc2, wc3)
        assertEquals(Part("", 7, "la"), wc12)
        assertEquals(Part("wn", 6, ""), wc23)

        // identity element
        assertEquals(wc1, m.combine(wc1, m.nil))
        assertEquals(wc1, m.combine(m.nil, wc1))
        assertEquals(wc2, m.combine(wc2, m.nil))
        assertEquals(wc2, m.combine(m.nil, wc2))
        assertEquals(wc3, m.combine(wc3, m.nil))
        assertEquals(wc3, m.combine(m.nil, wc3))

        // associativity
        val wc123a = m.combine(wc12, wc3)
        val wc123b = m.combine(wc1, wc23)
        assertEquals(wc123a, wc123b)
        assertEquals(Part("", 9, ""), wc123a)
    }
}
