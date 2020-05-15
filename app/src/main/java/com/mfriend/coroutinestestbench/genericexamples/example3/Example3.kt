package example3

import example2.getNum
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val numOneDeferred: Deferred<Int> = async { getNum(1) }
    val numTwoDeferred: Deferred<Int> = async { getNum(2) }
    println("Launched two coroutines continuing after ${System.currentTimeMillis() - startTime}")
    val result = numOneDeferred.await() + numTwoDeferred.await()
    println("Computed result: $result after ${System.currentTimeMillis() - startTime}")
}

