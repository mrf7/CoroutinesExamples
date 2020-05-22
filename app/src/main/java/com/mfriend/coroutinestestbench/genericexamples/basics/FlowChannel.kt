package com.mfriend.coroutinestestbench.genericexamples.basics

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.collect

fun main() = runBlockingUnit {
    val subscribePoc = SubscribePoc()
    launch {
        println("subbing")
        subscribePoc.subscribeTo("A").collect {
            println("A got $it")
        }
    }
    launch {
        println("subbing")
        subscribePoc.subscribeTo("A").collect {
            println("A2 got $it")
        }
    }

    (1..10).map {
        delay(100)
        subscribePoc.zoneUpdate(Zone("A", it))
    }
    delay(1000)
    println("done")
}


fun <T> runBlockingUnit(block: suspend CoroutineScope.() -> T) = runBlocking {
    block()
    Unit
}

data class Zone(val name: String, val level: Int)

fun <T> toFlow(obs: Observable<T>): Flow<T> = flow { obs.collect { emit(it) } }

class SubscribePoc {
    private val subject = PublishSubject.create<Zone>()

    fun subscribeTo(zoneName: String) = toFlow(subject).filter { it.name == zoneName }

    fun zoneUpdate(zone: Zone) {
        subject.onNext(zone)
    }
}