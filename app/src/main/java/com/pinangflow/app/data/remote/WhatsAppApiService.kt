package com.pinangflow.app.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface WhatsAppApiService {
    @FormUrlEncoded
    @POST("send")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Field("target") target: String,
        @Field("message") message: String
    ): Response<ResponseBody>
}
