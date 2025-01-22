package com.lohanna.catalogodefilmes.data.common.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRemoteService(baseUrl: String) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}