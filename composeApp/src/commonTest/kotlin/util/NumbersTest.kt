package fi.tuska.beerclock.util

import kotlin.test.Test
import kotlin.test.assertEquals

class NumbersTest {
    @Test
    fun shouldPadInts() {
        assertEquals("0003", 3.zeroPad(4))
        assertEquals("1234", 1234.zeroPad(4))
        assertEquals("1234", 1234.zeroPad(3))
        assertEquals("1234", 1234.zeroPad(1))
        assertEquals("01234", 1234.zeroPad(5))
    }
}
