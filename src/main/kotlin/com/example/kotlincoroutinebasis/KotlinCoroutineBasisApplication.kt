package com.example.kotlincoroutinebasis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinCoroutineBasisApplication

fun main(args: Array<String>) {
    runApplication<KotlinCoroutineBasisApplication>(*args)
}

fun printlnWithThreadName(message: String) {
    println("[${Thread.currentThread().name}] : $message")
}