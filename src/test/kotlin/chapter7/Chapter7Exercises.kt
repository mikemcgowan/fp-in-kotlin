package chapter7

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Chapter7Exercises {

    @Test
    fun splitAt() {
        assertEquals(listOf(1, 2) to listOf(3, 4), listOf(1, 2, 3, 4).splitAt(2))
        assertEquals(listOf(1, 2, 3) to listOf(4, 5), listOf(1, 2, 3, 4, 5).splitAt(3))
    }
}
