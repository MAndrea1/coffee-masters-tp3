package app.itmaster.mobile.coffeemasters

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.itmaster.mobile.coffeemasters.data.DataManager
import app.itmaster.mobile.coffeemasters.composables.InfoPage
import app.itmaster.mobile.coffeemasters.composables.MenuPage
import app.itmaster.mobile.coffeemasters.composables.OffersPage
import app.itmaster.mobile.coffeemasters.composables.OrderPage
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(dataManager: DataManager) {
    var currentPage = remember { mutableStateOf("menu") }
    var snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
               TopAppBar(
                        title = {
                            Box(contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()) {
                                Image(painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Coffee Masters")
                                }
                        },
                   colors = TopAppBarDefaults.smallTopAppBarColors(
                       containerColor = MaterialTheme.colorScheme.primary
                   )
               )},
        content = {
            Box(modifier =
                Modifier
                    .padding(bottom = BottomBarUI.marginBottom, top = 64.dp)
            ) {
                when (currentPage.value) {
                    "menu" -> MenuPage(dataManager)
                    "offers" -> OffersPage()
                    "order" -> OrderPage(dataManager, goToMenu = {
                        println("new order")
                        currentPage.value = "menu"
                    }, finishedOrder = {
                        coroutineScope.launch {
                            currentPage.value = "menu"
                            dataManager.clearCart()
                            snackbarHostState.showSnackbar("Order Nª $it sent successfully!")
                        }
                    })
                    "info" -> InfoPage()
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    SnackbarHost(hostState = snackbarHostState)
                }
            }
        },
        bottomBar = {
            NavBar(currentPage.value, onRouteChange = {
                currentPage.value = it
            })
        }
    )
}