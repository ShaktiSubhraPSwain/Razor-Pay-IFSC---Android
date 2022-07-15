package com.example.razorpayifsc.domain.common

import com.example.razorpayifsc.domain.common.network.NetworkResponse
import kotlinx.coroutines.*
import java.lang.Error

abstract class BaseCoroutinesUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): NetworkResponse<Type, Error>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (NetworkResponse<Type, Error>) -> Unit = {}
    ) {
        val backgroundJob = scope.async(Dispatchers.IO) { run(params) }
        scope.launch(Dispatchers.Main) { onResult(backgroundJob.await()) }
    }
}
