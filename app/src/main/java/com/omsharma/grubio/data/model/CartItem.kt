package com.omsharma.grubio.data.model

data class CartItem(
    val addedAt: String,
    val id: String,
    val menuItemId: FoodItem,
    val quantity: Int,
    val restaurantId: String,
    val userId: String
)