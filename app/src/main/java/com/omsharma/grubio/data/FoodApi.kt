package com.omsharma.grubio.data

import com.omsharma.grubio.data.model.AuthResponse
import com.omsharma.grubio.data.model.CategoriesResponse
import com.omsharma.grubio.data.model.LoginRequest
import com.omsharma.grubio.data.model.OAuthRequest
import com.omsharma.grubio.data.model.ResturauntsResponse
import com.omsharma.grubio.data.model.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FoodApi {
    @GET("/categories")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("/restaurants")
    suspend fun getRestaurants(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<ResturauntsResponse>

    @POST("/auth/signup")
    suspend fun signUp(@Body request: SignupRequest): Response<AuthResponse>

    @POST("/auth/login")
    suspend fun signIn(@Body request: LoginRequest): Response<AuthResponse>

    @POST("/auth/oauth")
    suspend fun oAuth(@Body request: OAuthRequest): Response<AuthResponse>
}