package com.mfriend.coroutinestestbench.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class MainViewModel : ViewModel() {

    /**
     *  Show message
     */
    val message: LiveData<String>
        get() = _message

    private val _message = MutableLiveData<String>()

    fun launchDontExtend() {
        viewModelScope.launch {
            val aDeffered = viewModelScope.async {
                withTimeout(200) {
                    delay(300)
                    "A Succ"
                }
            }

            val bDeffered = viewModelScope.async {
                withTimeout(100) {
                    delay(50)
                    "B Succ"
                }
            }

            val aResult = try {
                aDeffered.await()
            } catch (e: Exception) {
                "Failed: ${e.message}"
            }
            val bResult = try {
                bDeffered.await()
            } catch (e: Exception) {
                "Failed: ${e.message}"
            }
            showResults(aResult, bResult)
        }
    }

    fun launchExtend() {
        val aDeferred = viewModelScope.async {
            callableA()
        }

        viewModelScope.launch {
            val time = measureTimeMillis {
                val bResult = callableB()
                if (bResult == "extend") {
                    withTimeout(1000) {
                        aDeferred.await()
                    }
                } else {
                    aDeferred.cancel()
                }
                val aResult = try {
                    aDeferred.await()
                } catch (e: Exception) {
                    "Failed: ${e.message}"
                }
                showResults(aResult, bResult)
            }
            Log.d("MRF", "Took $time")
        }
    }

    fun launchExtendThenTimeout() {
        viewModelScope.launch {
            val time = measureTimeMillis {
                (1..100).toList().map {
                    viewModelScope.async {
                        delay(100)
                        it
                    }
                }.forEach { it.await() }
            }
            Log.d("MRF", "Took $time")
        }
    }

    fun otherTry() {
        viewModelScope.launch {
            // Start callable A and callable B in parallel
            val aDeffered = async {
                withTimeout(1000) {
                    callableA()
                }
            }
            val bDeffered = async {
                withTimeout(500) {
                    callableB()
                }
            }

            // Wait for the result of B, defaulting to "cancelled" if the call timed out
            val bResult = try {
                bDeffered.await()
            } catch (e: TimeoutCancellationException) {
                "cancelled"
            }
            // if the result of B indicates to keep waiting for A, wait for it. else cancel A
            if (bResult == "extend") {
                val aResult = aDeffered.await()
                //handle aResult
            } else {
                aDeffered.cancel()
            }
        }
    }

    suspend fun callableA(): String {
        Log.d("MRF", "Started a")
        delay(200)
        return "a Succ"
    }

    suspend fun callableB(): String {
        Log.d("MRF", "Started b")
        delay(100)
        return "no extend"
    }

    private fun showResults(aResult: String, bResult: String) {
        _message.value =
            """
                Result of A was: $aResult
                Result of B was: $bResult
            """.trimIndent()
        Log.d("MRF", _message.value!!)
    }
}
