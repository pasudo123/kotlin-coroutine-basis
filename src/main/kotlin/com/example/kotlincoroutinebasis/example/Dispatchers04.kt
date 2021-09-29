package com.example.kotlincoroutinebasis.example

import com.example.kotlincoroutinebasis.printlnWithThreadName
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * 코루틴 컨텍스트 내에 Job, Dispatchers 가 있다.
 */
fun main() = runBlocking<Unit> {
    printlnWithThreadName("My Job is ${coroutineContext[Job]}")
    printlnWithThreadName("${coroutineContext[Job]?.isActive}")

    async {
        printlnWithThreadName("${coroutineContext[Job]}")
    }
}