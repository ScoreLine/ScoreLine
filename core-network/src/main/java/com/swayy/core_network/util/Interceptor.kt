package com.swayy.core_network.util

import okhttp3.Interceptor
import okhttp3.Response

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-apisports-key", "280f84e4007818a22a2db7140843ae4f")
            .addHeader("x-apisports-host", "v3.football.api-sports.io")
            .build()
        return chain.proceed(request)
    }
}