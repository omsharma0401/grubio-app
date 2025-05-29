package com.omsharma.grubio.data.model

data class UpdateCartItemRequest(
    val cartItemId: String,
    val quantity: Int
)
