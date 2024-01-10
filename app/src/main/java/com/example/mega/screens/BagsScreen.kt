package com.example.mega.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.mega.DataStoreManager
import com.example.mega.UserInfo
import com.example.mega.baseUrl
import com.example.mega.components.BagCard
import com.example.mega.components.BagModel
import com.example.mega.components.CompanyShareCard
import com.example.mega.components.CompanyShareModel
import okhttp3.internal.immutableListOf
import org.jetbrains.annotations.Nullable
import org.json.JSONException

@Composable
fun BagsScreen(){
    val context = LocalContext.current
    val state = DataStoreManager(context)

    val data = remember {
        mutableStateListOf<BagModel>()
    }

    val userInfo = remember {
        mutableStateOf(UserInfo(false, "", ""))
    }

    val selectedBag = remember {
        mutableStateOf<BagModel?>(null)
    }

    LaunchedEffect(key1 = true) {
        state.getAuthInfo().collect { info ->
            userInfo.value = info
        }
    }

    if(userInfo.value.userId != ""){
        getUserBagsList(userInfo.value.userId, data, context)
    }

    if(selectedBag.value == null){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                data
            ) { _, item ->
                BagCard(item) {
                    selectedBag.value = item
                }
            }
        }
    }
    else {
        BagActionsScreen(selectedBag.value!!){
            selectedBag.value = null
        }
    }

}

fun getUserBagsList(userId: String, state: MutableList<BagModel>, context: Context) {
    val url = "$baseUrl/bags?userId=$userId"
    val queue = Volley.newRequestQueue(context)
    val jsonArrayRequest = JsonArrayRequest(
        Request.Method.GET,
        url,
        null,
        { response ->
            state.clear()
            for (i in 0 until response.length()) {
                try {
                    // we are getting each json object.
                    val responseObj = response.getJSONObject(i)

                    val id: String = responseObj.getString("id")
                    val userId: String = responseObj.getString("userId")
                    val name: String = responseObj.getString("name")

                    val item = BagModel(id, userId, name)

                    state.add(
                        item
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        },
        { error ->

        }

    )
    queue.add(jsonArrayRequest)
}
