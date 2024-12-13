package com.capstone.sampahin.data.api

import com.capstone.sampahin.data.history.HistoryResponse
import com.capstone.sampahin.data.login.LoginRequest
import com.capstone.sampahin.data.login.LoginResponse
import com.capstone.sampahin.data.predict.PredictResponse
import com.capstone.sampahin.data.register.RegisterRequest
import com.capstone.sampahin.data.register.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MLApiService {
    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part file: MultipartBody.Part
    ): PredictResponse

    @GET("history")
    suspend fun getHistory(): HistoryResponse
}
