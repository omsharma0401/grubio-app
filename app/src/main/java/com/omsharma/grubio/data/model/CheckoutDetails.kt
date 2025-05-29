package com.omsharma.grubio.data.model

data class CheckoutDetails(
    val deliveryFee: Double,
    val subTotal: Double,
    val tax: Double,
    val totalAmount: Double
)