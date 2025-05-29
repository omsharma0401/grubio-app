package com.omsharma.grubio.ui.features.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.omsharma.grubio.ui.features.food_item_details.FoodItemCounter
import com.omsharma.grubio.R
import com.omsharma.grubio.data.model.Address
import com.omsharma.grubio.data.model.CartItem
import com.omsharma.grubio.data.model.CheckoutDetails
import com.omsharma.grubio.data.utils.StringUtils
import com.omsharma.grubio.ui.BasicDialog
import com.omsharma.grubio.ui.navigation.AddressList
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val showErrorDialog = remember {
        mutableStateOf(
            false
        )
    }
    val address =
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<Address?>(
            "address",
            null
        )
            ?.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = address?.value) {
        address?.value?.let {
            viewModel.onAddressSelected(it)
        }

    }
    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest {
            when (it) {
                is CartViewModel.CartEvent.onItemRemoveError,
                is CartViewModel.CartEvent.onQuantityUpdateError,
                is CartViewModel.CartEvent.showErrorDialog -> {
                    showErrorDialog.value = true
                }

                is CartViewModel.CartEvent.onAddressClicked -> {
                    navController.navigate(AddressList)
                }

                else -> {

                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        CartHeaderView(onBack = { navController.popBackStack() })
        Spacer(modifier = Modifier.size(16.dp))
        when (uiState.value) {
            is CartViewModel.CartUiState.Loading -> {
                Spacer(modifier = Modifier.size(16.dp))
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.size(16.dp))
                    CircularProgressIndicator()
                }
            }

            is CartViewModel.CartUiState.Success -> {
                val data = (uiState.value as CartViewModel.CartUiState.Success).data
                if (data.items.isNotEmpty()) {
                    LazyColumn {
                        items(data.items) { it ->
                            CartItemView(cartItem = it, onIncrement = { cartItem, _ ->
                                viewModel.incrementQuantity(cartItem)
                            }, onDecrement = { cartItem, _ ->
                                viewModel.decrementQuantity(cartItem)
                            }, onRemove = {
                                viewModel.removeItem(it)
                            })
                        }
                        item {
                            CheckoutDetailsView(data.checkoutDetails)
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = null,
                            tint = Color.Gray
                        )
                        Text(
                            text = "No items in cart",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }


            is CartViewModel.CartUiState.Error -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val message = (uiState.value as CartViewModel.CartUiState.Error).message
                    Text(text = message, style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Retry")
                    }

                }
            }

            CartViewModel.CartUiState.Nothing -> {}
        }
        val selectedAddress = viewModel.selectedAddress.collectAsStateWithLifecycle()
        Spacer(modifier = Modifier.weight(1f))
        if (uiState.value is CartViewModel.CartUiState.Success) {
            AddressCard(selectedAddress.value) {
                viewModel.onAddressClicked()
            }
            Button(
                onClick = { viewModel.checkout() },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedAddress.value != null
            ) {
                Text(text = "Checkout")
            }
        }

    }

    if (showErrorDialog.value) {
        ModalBottomSheet(onDismissRequest = { showErrorDialog.value = false }) {
            BasicDialog(title = viewModel.errorTitle, description = viewModel.errorMessage) {
                showErrorDialog.value = false
            }
        }
    }

}

@Composable
fun AddressCard(address: Address?, onAddressClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(Color.White)
            .clickable { onAddressClicked.invoke() }
            .padding(16.dp)

    ) {
        if (address != null) {
            Column {
                Text(text = address.addressLine1, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "${address.city}, ${address.state}, ${address.country}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        } else {
            Text(text = "Select Address", style = MaterialTheme.typography.bodyMedium)
        }
    }

}

@Composable
fun CheckoutDetailsView(checkoutDetails: CheckoutDetails) {
    Column {
        CheckoutRowItem(title = "SubTotal", value = checkoutDetails.subTotal, currency = "USD")
        CheckoutRowItem(title = "Tax", value = checkoutDetails.tax, currency = "USD")
        CheckoutRowItem(
            title = "Delivery Fee", value = checkoutDetails.deliveryFee, currency = "USD"
        )
        CheckoutRowItem(title = "Total", value = checkoutDetails.totalAmount, currency = "USD")
    }
}

@Composable
fun CheckoutRowItem(title: String, value: Double, currency: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = StringUtils.formatCurrency(value),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = currency,
                style = MaterialTheme.typography.titleMedium,
                color = Color.LightGray
            )
        }
        VerticalDivider()
    }

}

@Composable
fun CartItemView(
    cartItem: CartItem,
    onIncrement: (CartItem, Int) -> Unit,
    onDecrement: (CartItem, Int) -> Unit,
    onRemove: (CartItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = cartItem.menuItemId.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(82.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = cartItem.menuItemId.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { onRemove.invoke(cartItem) }, modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
            Text(
                text = cartItem.menuItemId.description,
                maxLines = 1,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$${cartItem.menuItemId.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                FoodItemCounter(count = cartItem.quantity,
                    onCounterIncrement = { onIncrement.invoke(cartItem, cartItem.quantity) },
                    onCounterDecrement = { onDecrement.invoke(cartItem, cartItem.quantity) })
            }
        }
    }
}

@Composable
fun CartHeaderView(onBack: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = onBack) {
            Image(painter = painterResource(id = R.drawable.back), contentDescription = null)
        }
        Text(text = "Cart", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(8.dp))
    }
}