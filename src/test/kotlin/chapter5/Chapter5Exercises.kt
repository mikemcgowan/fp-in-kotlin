package chapter5

import chapter3.List
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Chapter5Exercises {

    @Test
    fun exercise5dot1() {
        assertEquals(List.of(1, 2, 3, 4), Stream.of(1, 2, 3, 4).toList())
    }
}
