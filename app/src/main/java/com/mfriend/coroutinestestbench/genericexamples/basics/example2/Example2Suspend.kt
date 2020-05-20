package com.mfriend.coroutinestestbench.genericexamples.basics.example2

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * In  this example we are calling a suspend function from within a coroutine and we can see that we suspend while
 * waiting for the result of getNum(). By convention, all suspend functions should be safe to call from the main thread.
 * This means that if getNum() did any heavy computation or blocking IO calls, it would be responsible for switching to a
 * different thread so calling it from main is always safe.
 */
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val one = getNum(1)
    // The line above will suspend until getOne returns. So we wont move on to the next line until
    // after getOne is done
    println("first call returned after ${System.currentTimeMillis() - startTime}, calling again")
    val two = getNum(2)
    println("got $one then $two, which took ${System.currentTimeMillis() - startTime} total")
}

suspend fun getNum(value: Int): Int {
    delay(500)
    println("finished com.mfriend.coroutinestestbench.genericexamples.basics.example2.getNum($value)")
    return value
}
