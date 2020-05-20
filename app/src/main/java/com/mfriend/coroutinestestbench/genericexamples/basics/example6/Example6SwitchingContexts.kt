package com.mfriend.coroutinestestbench.genericexamples.basics.example6

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        // Creating a coroutine scope with a name, a Job and a dispatcher using +
        // All coroutines launched with this job will inherit the context i.e. have the name "TopScope" and run on the main thread
        // Note since we're not running in android Dispatchers.Main isnt defined so using newSingleThreadContext to mimic it
        val myScope = CoroutineScope(Job() + CoroutineName("Top scope") + newSingleThreadContext("main"))

        // This coroutine inherits all values but job from the scope
        myScope.launch {
            println("Coroutine 1 context: $coroutineContext")
            // The inheritance is also passed down to nested coroutines
            launch {
                println("Coroutine 1a context: $coroutineContext")
            }
        }.join()// join the job so we can get the prints in order

        // This scope overwrites the dispatcher to run on another thread but keeps everything else
        myScope.launch(Dispatchers.IO) {
            println("Coroutine 2 context: $coroutineContext")
            // We can also adjust the scope in nested coroutines
            launch(CoroutineName("2a")) {
                println("Coroutine 2a context: $coroutineContext")
            }
        }.join() // join the job so we get the prints in order
    }
}

