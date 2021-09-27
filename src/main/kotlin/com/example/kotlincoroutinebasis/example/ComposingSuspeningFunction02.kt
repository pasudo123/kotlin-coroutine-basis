package com.example.kotlincoroutinebasis.example

import com.example.kotlincoroutinebasis.printlnWithThreadName
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
[main @coroutine#2] : Useful[1]
[main @coroutine#3] : Useful[2]
[main @coroutine#1] : result :: 42
[main @coroutine#1] : Completed 1065 ms

 async 블록을 붙였기 때문에 속도가 이전보다 더 빠르다.
 await 를 이용하여 나중에 결과값을 받을 수 있다.
 */
fun main() {
    runBlocking {
        val time = measureTimeMillis {
            val oneDeferred = async { doSomethingUsefulOne() }
            val twoDeferred = async { doSomethingUsefulTwo() }
            printlnWithThreadName("result :: ${oneDeferred.await() + twoDeferred.await()}")
        }
        printlnWithThreadName("Completed $time ms")
    }
}