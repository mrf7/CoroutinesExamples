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


}
