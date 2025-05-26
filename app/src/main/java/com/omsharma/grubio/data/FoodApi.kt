package com.omsharma.grubio.data

import com.omsharma.grubio.data.model.AuthResponse
import com.omsharma.grubio.data.model.SignupRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApi {
    @GET("/food")
    suspend fun getFood(): List<String>

    @POST("/auth/signup")
    suspend fun signup(@Body request: SignupRequest): AuthResponse
}