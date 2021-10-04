package com.example.kotlincoroutinebasis.example

import com.example.kotlincoroutinebasis.printlnWithThreadName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Flow02 {
    fun main() = runBlocking {

        launch {
            for (k in 1..3) {
                printlnWithThreadName("I'm not blocked $k")
                delay(100L)
            }
        }
    }
}

