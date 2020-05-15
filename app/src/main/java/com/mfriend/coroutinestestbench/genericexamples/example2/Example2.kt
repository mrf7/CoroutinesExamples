package example2

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

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
    println("finished example2.getNum($value)")
    return value
}
