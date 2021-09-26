package com.example.kotlincoroutinebasis.example

import com.example.kotlincoroutinebasis.printlnWithThreadName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
[main @coroutine#1] : Hello
[main @coroutine#2] : World !!
 */
fun main() = runBlocking {
    doWorld03()
}

suspend fun doWorld03() = coroutineScope {
    launch {
        delay(1000L)
        printlnWithThreadName("World !!")
    }
    printlnWithThreadName("Hello")
}