package com.mfriend.coroutinestestbench.genericexamples.basics.example1

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * In this example, the code inside the coroutine also runs in the main thread, but since delay suspends instead of
 * blocking the thread, that thread is freed up to run the code after the launch.
 */
fun main() = runBlocking { // "this" = coroutine scope created by runBlocking
    val job = launch {
        // Here delay emulates some other suspend function that does actual work
        delay(1000)
        println("Finished the code in launch. Ran in ${Thread.currentThread().name}")
    }
    println("Launched coroutine, running in ${Thread.currentThread().name}")
}