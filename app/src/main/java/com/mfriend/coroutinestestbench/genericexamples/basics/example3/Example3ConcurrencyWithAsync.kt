package com.mfriend.coroutinestestbench.genericexamples.basics.example3

import com.mfriend.coroutinestestbench.genericexamples.basics.example2.getNum
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * This example uses getNum from above but this time it runs the two calls concurrently using async then uses await to
 * get the result. Instead of waiting for the first call to finish, we immediately make the second call and let them
 * run at the same time. This lets us do the same work in half the time
 */
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val numOneDeferred: Deferred<Int> = async { getNum(1) }
    val numTwoDeferred: Deferred<Int> = async { getNum(2) }
    println("Launched two coroutines continuing after ${System.currentTimeMillis() - startTime}")
    val result = numOneDeferred.await() + numTwoDeferred.await()
    println("Computed result: $result after ${System.currentTimeMillis() - startTime}")
}

