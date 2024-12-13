package com.capstone.sampahin.data.api

import com.capstone.sampahin.data.login.LoginRequest
import com.capstone.sampahin.data.login.LoginResponse
import com.capstone.sampahin.data.maps.MapsRequest
import com.capstone.sampahin.data.maps.MapsResponses
import com.capstone.sampahin.data.register.RegisterRequest
import com.capstone.sampahin.data.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("find_places")
    suspend fun findPlaces(
        @Body request: MapsRequest
    ): MapsResponses
}