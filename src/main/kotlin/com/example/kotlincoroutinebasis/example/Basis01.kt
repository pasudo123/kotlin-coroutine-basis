package com.example.kotlincoroutinebasis.example

import com.example.kotlincoroutinebasis.printlnWithThreadName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#1] : Hello
[main @coroutine#2] : World !!
[main] : Outer
 */
fun main() {
    runBlocking {
        launch {
            delay(1000L)
            printlnWithThreadName("World !!")
        }

        printlnWithThreadName("Hello ")
    }

    printlnWithThreadName("Outer")
}