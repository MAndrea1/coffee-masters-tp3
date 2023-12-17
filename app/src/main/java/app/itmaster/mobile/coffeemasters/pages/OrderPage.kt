package app.itmaster.mobile.coffeemasters.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.itmaster.mobile.coffeemasters.data.DataManager
import app.itmaster.mobile.coffeemasters.data.ItemInCart
import app.itmaster.mobile.coffeemasters.data.Product

@Composable
fun OrderPage(dataManager: DataManager, goToMenu: (String)->Unit) {
    Column (modifier =
    Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(16.dp),
        verticalArrangement = Arrangement.Top,
    ){
        if (dataManager.cart.isNotEmpty()) {
            Column(
                modifier = Modifier.weight(1f), // Occupy available space
            ) {
                Text("ITEM",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.primary)
                LazyColumn{
                    itemsIndexed(dataManager.cart) { index, item ->
                        OrderCard(item, index-1, dataManager::cartRemove)
                    }
                }
            }
            SendOrderForm()
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                content = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    Text("Cart empty",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize= 20.sp)
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        onClick = {
                            goToMenu("menu")
                        },
                        ) {
                            Text("Make your order")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun OrderCard(item: ItemInCart, index: Int, onRemove: (Product) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ){
        Row()
        {
        Text("${item.quantity}x",
            color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            item.product.name,
            color = Color.Black)
        }
        Row()
        {
            Text("${item.product.price * item.quantity}",
                color = Color.Black)
            Spacer(modifier = Modifier.size(15.dp))
            Image(Icons.Outlined.Delete,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "Delete",
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 1.dp)
                    .size(24.dp)
                    .clickable {
                        onRemove(item.product)
                    }
            )
        }
    }
    if (index < 1) {
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendOrderForm () {
    var text by rememberSaveable { mutableStateOf("") }
    Column {
        Text("NAME",
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary)
        OutlinedTextField(
            singleLine = true,
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text("Name for order") },
            shape = RoundedCornerShape(40.dp),
            colors =
            TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderPage_Preview() {
    val mockDataManager = MockDataManager()
    OrderPage(mockDataManager, goToMenu = { })
}

class MockDataManager: DataManager() {
    override var cart: List<ItemInCart> = listOf(
        ItemInCart((Product(1, "Cafe", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            1),
        ItemInCart((Product(1, "Cafe 2", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            2),
        ItemInCart((Product(1, "Cafe 3", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            3),
                ItemInCart((Product(1, "Cafe", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
        1),
    ItemInCart((Product(1, "Cafe 2", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
    2),
    ItemInCart((Product(1, "Cafe 3", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
    3),
            ItemInCart((Product(1, "Cafe", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
        1),
    ItemInCart((Product(1, "Cafe 2", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
    2),
    ItemInCart((Product(1, "Cafe 3", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
    3),
    ItemInCart((Product(1, "Cafe", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
    1),
    ItemInCart((Product(1, "Cafe 2", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
    2),
    ItemInCart((Product(1, "Cafe 3", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
    3),
        ItemInCart((Product(1, "Cafe", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            1),
        ItemInCart((Product(1, "Cafe 2", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            2),
        ItemInCart((Product(1, "Cafe 3", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            3),
        ItemInCart((Product(1, "Cafe", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            1),
        ItemInCart((Product(1, "Cafe 2", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            2),
        ItemInCart((Product(1, "Cafe 3", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            3),
        ItemInCart((Product(1, "Cafe", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            1),
        ItemInCart((Product(1, "Cafe 2", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            2),
        ItemInCart((Product(1, "Cafe 3", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            3),
        ItemInCart((Product(1, "Cafe", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            1),
        ItemInCart((Product(1, "Cafe 2", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            2),
        ItemInCart((Product(1, "Cafe 3", 10.0, "A spanish coffee", "https://firtman.github.io/coffeemasters/api/images/cappuccino.png")),
            3)
    )
}