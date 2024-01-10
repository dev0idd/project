package com.example.mega.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mega.DataStoreManager
import com.example.mega.UserInfo
import com.example.mega.baseUrl
import com.example.mega.screens.getUserBagsList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.util.UUID

@Composable
fun DeleteConfirmDialog(itemToDeleteId: String, onClose: ()->Unit) {

    val context = LocalContext.current
    val state = DataStoreManager(context)

    val userInfo = remember {
        mutableStateOf(UserInfo(false, "", ""))
    }

    LaunchedEffect(key1 = true) {
        state.getAuthInfo().collect { info ->
            userInfo.value = info
        }
    }

    AlertDialog(
        onDismissRequest = {
            onClose()
        },
        dismissButton = {
            TextButton(onClick = {
                onClose()
            }) {
                Text(text = "Отмена")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                removeFromBag(itemToDeleteId, context)
                onClose()
            }) {
                Text(text = "Удалить")
            }
        },
        title = {
            Text(text = "Удалить акции из портфеля?")
        }
    )
}


fun removeFromBag (itemId: String, context: Context) {
    val deleteUrl = "$baseUrl/bagsToActions/$itemId"
    val queue = Volley.newRequestQueue(context)

    val existCheck = StringRequest(
        Request.Method.DELETE,
        deleteUrl,
        {},
        {}
    )

    queue.add(existCheck)
}

