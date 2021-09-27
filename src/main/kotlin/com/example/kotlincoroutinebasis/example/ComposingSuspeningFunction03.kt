package com.example.kotlincoroutinebasis.example

import com.example.kotlincoroutinebasis.printlnWithThreadName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time = measureTimeMillis {
            val oneDeferred = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val twoDeferred = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }

            // start 가 있고 없고에 따라 async coroutine 을 동시에 실행하는지 여부를 결정할 수 있다.
            oneDeferred.start()
            twoDeferred.start()

            printlnWithThreadName("result :: ${oneDeferred.await() + twoDeferred.await()}")
        }
        printlnWithThreadName("Completed $time ms")
    }
}