package com.omsharma.grubio.ui.features.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omsharma.grubio.data.model.Address
import com.omsharma.grubio.data.model.CartItem
import com.omsharma.grubio.data.model.CartResponse
import com.omsharma.grubio.data.model.UpdateCartItemRequest
import com.omsharma.grubio.data.FoodApi
import com.omsharma.grubio.data.remote.ApiResponse
import com.omsharma.grubio.data.remote.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(val foodApi: FoodApi) : ViewModel() {

    var errorTitle: String = ""
    var errorMessage: String = ""
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _event = MutableSharedFlow<CartEvent>()
    val event = _event.asSharedFlow()
    private var cartResponse: CartResponse? = null
    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount = _cartItemCount.asStateFlow()

    private val address = MutableStateFlow<Address?>(null)
    val selectedAddress = address.asStateFlow()

    init {
        getCart()
    }


    fun getCart() {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            val res = safeApiCall { foodApi.getCart() }
            when (res) {
                is ApiResponse.Success -> {
                    cartResponse = res.data
                    _cartItemCount.value = res.data.items.size
                    _uiState.value = CartUiState.Success(res.data)
                }

                is ApiResponse.Error -> {
                    _uiState.value = CartUiState.Error(res.message)
                }

                else -> {
                    _uiState.value = CartUiState.Error("An error occurred")
                }
            }
        }
    }

    fun incrementQuantity(cartItem: CartItem) {
        if (cartItem.quantity == 5) {
            return
        }
        updateItemQuantity(cartItem, cartItem.quantity + 1)
    }

    fun decrementQuantity(cartItem: CartItem) {
        if (cartItem.quantity == 1) {
            return
        }
        updateItemQuantity(cartItem, cartItem.quantity - 1)
    }

    private fun updateItemQuantity(cartItem: CartItem, quantity: Int) {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            val res =
                safeApiCall { foodApi.updateCart(UpdateCartItemRequest(cartItem.id, quantity)) }
            when (res) {
                is ApiResponse.Success -> {
                    getCart()
                }

                else -> {
                    cartResponse?.let {
                        _uiState.value = CartUiState.Success(cartResponse!!)
                    }
                    errorTitle = "Cannot Update Quantity"
                    errorMessage = "An error occurred while updating the quantity of the item"
                    _event.emit(CartEvent.onQuantityUpdateError)
                }
            }
        }
    }

    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            val res =
                safeApiCall { foodApi.deleteCartItem(cartItem.id) }
            when (res) {
                is ApiResponse.Success -> {
                    getCart()
                }

                else -> {
                    cartResponse?.let {
                        _uiState.value = CartUiState.Success(cartResponse!!)
                    }
                    errorTitle = "Cannot Delete"
                    errorMessage = "An error occurred while deleting the item"
                    _event.emit(CartEvent.onItemRemoveError)
                }
            }
        }
    }

    fun checkout() {

    }

    fun onAddressClicked() {
        viewModelScope.launch {
            _event.emit(CartEvent.onAddressClicked)
        }
    }

    fun onAddressSelected(it: Address) {
        address.value = it
    }

    sealed class CartUiState {
        object Nothing : CartUiState()
        object Loading : CartUiState()
        data class Success(val data: CartResponse) : CartUiState()
        data class Error(val message: String) : CartUiState()
    }

    sealed class CartEvent {
        object showErrorDialog : CartEvent()
        object OnCheckout : CartEvent()
        object onQuantityUpdateError : CartEvent()
        object onItemRemoveError : CartEvent()
        object onAddressClicked : CartEvent()
    }
}