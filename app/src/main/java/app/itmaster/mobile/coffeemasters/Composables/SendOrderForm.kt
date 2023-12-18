package app.itmaster.mobile.coffeemasters.Composables

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.random.Random


private const val SHARED_PREF = "MyPrefs"
private const val USER_NAME_KEY = "user_name"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendOrderForm (onSend: ()->Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf(loadUserName(context)) }


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
                .padding(vertical = 8.dp),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (text.isNotEmpty()) {
                        saveUserName(context, text)
                        focusManager.clearFocus()
                        println("on done finished")
                        onSend()
                    }
                }
            )
        )
    }
}

private fun saveUserName(context: Context, name: String) {
    context.getSharedPreferences(SHARED_PREF, 0).edit().putString(USER_NAME_KEY, name).apply()
}

private fun loadUserName(context: Context): String {
    return context.getSharedPreferences(SHARED_PREF, 0).getString(USER_NAME_KEY, "") ?: ""
}
