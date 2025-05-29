package com.omsharma.grubio.data.model

data class AddToCartRequest(
    val restaurantId: String,
    val menuItemId: String,
    val quantity: Int
)
