package com.swayy.core_network.util

import okhttp3.Interceptor
import okhttp3.Response

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-apisports-key", "f53f4f26c069cc392990458476fbbb15")
            .addHeader("x-apisports-host", "v3.football.api-sports.io")
            .build()
        return chain.proceed(request)
    }
}