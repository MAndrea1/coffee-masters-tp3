package app.itmaster.mobile.coffeemasters.data

import android.content.ClipData.Item
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

open class DataManager: ViewModel() {
    var menu: List<Category> by mutableStateOf(listOf())
    open var cart: List<ItemInCart> by mutableStateOf(listOf())

    init {
        fetchData()
    }

    fun fetchData() {
        // Ejecuta el getMenu en una corutina (algo así como un thread)
        viewModelScope.launch {
            menu = API.menuService.getMenu()
        }
    }

    fun cartAdd(product: Product) {
        val existingItem = cart.find { it.product == product }

        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            cart = cart + ItemInCart(product, 1)
        }
        println(cart)
    }

    fun cartRemove(product: Product) {
        val updatedCart = cart.toMutableList()
        val existingItem = updatedCart.find { it.product == product }

        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                existingItem.quantity -= 1
            } else {
                updatedCart.remove(existingItem)
            }
        }

        cart = updatedCart
        println(cart)
    }

    fun clearCart() {
        cart = emptyList()
    }
}