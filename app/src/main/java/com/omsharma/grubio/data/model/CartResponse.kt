package com.omsharma.grubio.data.model

data class CartResponse(
    val checkoutDetails: CheckoutDetails,
    val items: List<CartItem>
)