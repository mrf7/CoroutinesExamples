package com.mfriend.coroutinestestbench.genericexamples.basics.example7

import com.mfriend.coroutinestestbench.genericexamples.basics.example2.getNum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val regularTime = measureTimeMillis { regularFun() }
        val parallelTime = measureTimeMillis { parallelWork() }
        val mainSafeTime = measureTimeMillis { stillMainSafe() }
        val notMainSafeTime = measureTimeMillis { notMainSafe() }
        println("regular: $regularTime\nparallel:$parallelTime\nmainSafe:$mainSafeTime\nnotMainSafe:$notMainSafeTime")
    }
}

// This function only calls other main safe suspend functions
suspend fun regularFun(): Int {
    delay(500)
    return 42
}

// This function uses coroutines to run work in parallel, but doesnt block the thread to wait on them
suspend fun parallelWork(): Int = coroutineScope {
    val result1 = async { getNum(1) }
    val result2 = async { getNum(2) }
    return@coroutineScope result1.await() + result2.await()
}

// This function blocks a thread,  so it needs to switch contexts to be main safe
suspend fun stillMainSafe(): Int = withContext(Dispatchers.IO) {
    launch {
        Thread.sleep(500)
    }
    launch {
        Thread.sleep(500)
    }
    return@withContext 1
}

// This function blocks the thread but doesnt switch contexts. Android studio gives an "
suspend fun notMainSafe(): Int = coroutineScope {
    launch {
        Thread.sleep(500)
    }
    launch {
        Thread.sleep(500)
    }
    return@coroutineScope 1
}
