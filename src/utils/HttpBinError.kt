package com.theobencode.utils

import io.ktor.http.HttpStatusCode

data class HttpBinError(
    val code: HttpStatusCode,
    val request: String,
    val message: String,
    val detailMessage: String? = null
)
