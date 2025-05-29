package com.omsharma.grubio.data

import com.omsharma.grubio.data.model.AddToCartRequest
import com.omsharma.grubio.data.model.AddToCartResponse
import com.omsharma.grubio.data.model.Address
import com.omsharma.grubio.data.model.AddressListResponse
import com.omsharma.grubio.data.model.AuthResponse
import com.omsharma.grubio.data.model.CartResponse
import com.omsharma.grubio.data.model.CategoriesResponse
import com.omsharma.grubio.data.model.FoodItemResponse
import com.omsharma.grubio.data.model.GenericMsgResponse
import com.omsharma.grubio.data.model.OAuthRequest
import com.omsharma.grubio.data.model.ResturauntsResponse
import com.omsharma.grubio.data.model.ReverseGeoCodeRequest
import com.omsharma.grubio.data.model.SignInRequest
import com.omsharma.grubio.data.model.SignUpRequest
import com.omsharma.grubio.data.model.UpdateCartItemRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
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
    suspend fun signUp(@Body request: SignUpRequest): Response<AuthResponse>

    @POST("/auth/login")
    suspend fun signIn(@Body request: SignInRequest): Response<AuthResponse>

    @POST("/auth/oauth")
    suspend fun oAuth(@Body request: OAuthRequest): Response<AuthResponse>

    @GET("/restaurants/{restaurantId}/menu")
    suspend fun getFoodItemForRestaurant(@Path("restaurantId") restaurantId: String): Response<FoodItemResponse>

    @POST("/cart")
    suspend fun addToCart(@Body request: AddToCartRequest): Response<AddToCartResponse>

    @GET("/cart")
    suspend fun getCart(): Response<CartResponse>

    @PATCH("/cart")
    suspend fun updateCart(@Body request: UpdateCartItemRequest): Response<GenericMsgResponse>

    @DELETE("/cart/{cartItemId}")
    suspend fun deleteCartItem(@Path("cartItemId") cartItemId: String): Response<GenericMsgResponse>

    @GET("/addresses")
    suspend fun getUserAddress(): Response<AddressListResponse>

    @POST("/addresses/reverse-geocode")
    suspend fun reverseGeocode(@Body request: ReverseGeoCodeRequest): Response<Address>

    @POST("/addresses")
    suspend fun storeAddress(@Body address: Address): Response<GenericMsgResponse>
}