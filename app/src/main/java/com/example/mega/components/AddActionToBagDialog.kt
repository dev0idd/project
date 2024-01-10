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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.android.volley.toolbox.Volley
import com.example.mega.DataStoreManager
import com.example.mega.UserInfo
import com.example.mega.baseUrl
import com.example.mega.screens.getUserBagsList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActionToBagDialog(action: CompanyShareModel, onClose: () -> Unit) {
    val count = remember {
        mutableStateOf("")
    }

    val options = remember {
        mutableListOf<BagModel>()
    }

    val selectedBag = remember {
        mutableStateOf<BagModel?>(null)
    }


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

    val countInt: Int = count.value.toIntOrNull() ?: 0


    val enabledAdd = selectedBag.value != null &&  countInt > 0 && userInfo.value.userId != ""

    if(userInfo.value.userId != ""){
        getUserBagsList(userInfo.value.userId, options, context)
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
                if(enabledAdd){
                    addToBag(action, selectedBag.value!!, countInt, context)
                    onClose()
                }
            }, enabled = enabledAdd) {
                Text(text = "Добавить")
            }
        },
        title = {
            Column {

                val context = LocalContext.current
                var expanded by remember { mutableStateOf(false) }

                Text(text = "Портфель")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            value = selectedBag.value?.name ?: "",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            options.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.name) },
                                    onClick = {
                                        selectedBag.value = item
                                        expanded = false
                                        Toast.makeText(context, item.name, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                )
                            }
                        }
                    }
                }

                Text(text = "Кол-во")
                TextField(
                    value = count.value,
                    onValueChange = { value -> count.value = value },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                )
            }
        }
    )
}

fun addToBag (action: CompanyShareModel, bag: BagModel, count: Int, context: Context) {
    val getUrl = "$baseUrl/bagsToActions?bagId=${bag.id}&id=${action.id}"
    val queue = Volley.newRequestQueue(context)

    fun addCount(id: String, prevCount: Int, count: Int): JsonObjectRequest {
        val jsonBody = JSONObject()
        jsonBody.put("count", (prevCount + count).toString())
        val url = "$baseUrl/bagsToActions/$id"

        return JsonObjectRequest(Request.Method.PATCH, url, jsonBody, {}, {})
    }

    fun addToBag(action: CompanyShareModel, count: Int, bag: BagModel): JsonObjectRequest {
        val body = JSONObject(Json.encodeToString(value=action))
        val url = "$baseUrl/bagsToActions"

        body.put("uuid", action.id)
        body.put("bagId", bag.id)
        body.put("id", UUID.randomUUID())
        body.put("count", count)

        return JsonObjectRequest(Request.Method.POST, url, body, {}, {})
    }

    val existCheck = JsonArrayRequest(
        Request.Method.GET,
        getUrl,
        null,
        { response ->
            if(response.length() > 0){
                val responseObj = response.getJSONObject(0)
                val id = responseObj.getString("id")
                val prevCount = responseObj.getInt("count")

                val request = addCount(id, prevCount, count)

                queue.add(request)

            } else {
                val request = addToBag(action, count, bag)
                queue.add(request)
            }
        },
        { error ->

        }

    )

    queue.add(existCheck)
}
