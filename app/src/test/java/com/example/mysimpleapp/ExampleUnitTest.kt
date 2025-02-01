package com.example.mysimpleapp

import org.junit.Test

import org.junit.Assert.*


class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun text_isExpected() {
        val expected = "Hello, DevOps!"
        val actual = "Hello, DevOps!"
        assertEquals(expected, actual)
    }
}

