package com.example.kotlincoroutinebasis.example

import com.example.kotlincoroutinebasis.printlnWithThreadName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield

/**
[main @coroutine#1] : I'm sleeping 0 ...
[main @coroutine#1] : I'm sleeping 1 ...
[main @coroutine#1] : I'm sleeping 2 ...
Exception in thread "main" kotlinx.coroutines.TimeoutCancellationException: Timed out waiting for 1300 ms
 */
class CancelAndTimeoutExample07 {
    fun main() = runBlocking {
        // 메인에서 실행해서 앱이 죽어버려서 TimeoutCancellationException 에러가 발생한다.
        // CancellationException 서브클래스가 TimeoutCancellationException 다.
        // 익셉션을 막기위해선 withTimeout {} 전체를 try catch 문으로 감싸주어야 한다.
        withTimeout(1300L) {
            repeat(1000) { i ->
                printlnWithThreadName("I'm sleeping $i ...")
                delay(500L)
            }
        }
    }
}