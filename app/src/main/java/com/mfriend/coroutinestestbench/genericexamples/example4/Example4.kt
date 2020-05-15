package example4

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    // Launch 100,000 coroutines that each delay for 500ms then return the number passed
    val deferreds: List<Deferred<Int>> = (1..100_000).map {
        async { getNum(it) }
    }
    // Wait for all the coroutines to finish
    val results: List<Int> = deferreds.map { it.await() }
    val sum = results.sum()
    println("Got result $sum in ${System.currentTimeMillis() - startTime}")
}

suspend fun getNum(number: Int): Int {
    delay(500)
    return number
}