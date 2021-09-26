package com.example.kotlincoroutinebasis.example

import com.example.kotlincoroutinebasis.printlnWithThreadName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

/**
 */
fun main() = runBlocking {

    val job = launch {
        try {
            repeat(1000) { i ->
                printlnWithThreadName("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            // 리소스를 해제하기 위한 block 은 finally 에서 수행하도록 한다.
            printlnWithThreadName("job: I'm running finally")
        }
    }

    delay(1300L)
    printlnWithThreadName("main : I'm tired of waiting !")
    job.cancelAndJoin()
    printlnWithThreadName("main : now i can quit")
}