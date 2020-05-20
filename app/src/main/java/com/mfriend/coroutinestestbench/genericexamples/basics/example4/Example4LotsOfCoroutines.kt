package com.mfriend.coroutinestestbench.genericexamples.basics.example4

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * This example shows that since coroutines are lightweight, we can launch a lot of them without worry.
 * Despite the fact we have 100,000 coroutines waiting for 500ms we still complete quickly. If we tried to do the same
 * thing with this many threads, we'd likely crash.
 *
 * Note on awaitAll in this example: This is NOT equivalent to deferreds.map { it.await() }.
 * deferreds.map { it.await() } will wait for the completion in the order they deferreds show up in the list. If one
 * coroutine fails, deferreds.map { it.await() } will fail until it has received the result from every coroutine
 * before the failing one in the list.
 * awaitAll() on the other hand will fast. If any coroutine fails, awaitAll() will immediately fail which will cancel
 * all coroutines in the list.
 */
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    // Launch 100,000 coroutines that each delay for 500ms then return the number passed
    val deferreds: List<Deferred<Int>> = (1..100_000).map {
        async { getNum(it) }
    }
    // Wait for all the coroutines to finish

    val results: List<Int> = deferreds.awaitAll()
    val sum = results.sum()
    println("Got result $sum in ${System.currentTimeMillis() - startTime}")
}

suspend fun getNum(number: Int): Int {
    delay(500)
    return number
}