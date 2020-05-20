package com.mfriend.coroutinestestbench.genericexamples.basics.example5

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val myScope = CoroutineScope(Job())
        myScope.launch {
            println("Starting 1")
            delay(400)
            println("Launch 1 done")
        }

        myScope.launch {
            println("Starting 2")
            delay(1000)
            println("Launch 2 done")
        }

        val blockingJob = myScope.launch {
            println("Starting blocking")
            Thread.sleep(1000)
            println("Blocking call done")
        }

        delay(500)
        myScope.cancel()
        println("Canceled Scopes")
        myScope.launch {
            println("launching after cancel")
        }
        // Join the blocking job to wait for it to finish
        blockingJob.join()
    }
}