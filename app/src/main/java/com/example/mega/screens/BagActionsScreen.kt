package com.example.mega.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.volley.Header
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.mega.DataStoreManager
import com.example.mega.UserInfo
import com.example.mega.baseUrl
import com.example.mega.components.BagActionCard
import com.example.mega.components.BagActionModel
import com.example.mega.components.BagCard
import com.example.mega.components.BagModel
import com.example.mega.components.DeleteConfirmDialog
import org.json.JSONException

@Composable
fun BagActionsScreen(bag: BagModel, onBack: () -> Unit) {
    val context = LocalContext.current
    val state = DataStoreManager(context)

    val data = remember {
        mutableStateListOf<BagActionModel>()
    }

    val userInfo = remember {
        mutableStateOf(UserInfo(false, "", ""))
    }

    val totalSum = remember {
        mutableDoubleStateOf(0.0)
    }

    val actionsToDelete = remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(key1 = true) {
        state.getAuthInfo().collect { info ->
            userInfo.value = info
        }
    }

    getBagActionsList(bag.id, data, totalSum, context)

    if(actionsToDelete.value != null){
        DeleteConfirmDialog(actionsToDelete.value!!){
            actionsToDelete.value = null

        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(onClick = {onBack()}) {
                Text(text = "<-")
            }

            Text(text = bag.name)

            Text(text = "%.2f".format(totalSum.value) + " USD")
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                data
            ) { _, item ->
                BagActionCard(item){
                    actionsToDelete.value = item.id
                }
            }
        }
    }

}

fun getBagActionsList(bagId: String, state: MutableList<BagActionModel>, sumState: MutableDoubleState, context: Context) {
    val url = "$baseUrl/bagsToActions?bagId=$bagId"
    var queue = Volley.newRequestQueue(context)
    val jsonArrayRequest = JsonArrayRequest(
        Request.Method.GET,
        url,
        null,
        { response ->
            state.clear()
            var sum: Double = 0.0
            for (i in 0 until response.length()) {
                try {
                    // we are getting each json object.
                    val responseObj = response.getJSONObject(i)

                    val name = responseObj.getString("showName")
                    val count = responseObj.getInt("count")
                    val price = responseObj.getDouble("price")
                    val id = responseObj.getString("id")

                    val item = BagActionModel(name, price, count, id)

                    sum += price * count

                    state.add(
                        item
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            sumState.value = sum
        },
        { error ->

        }

    )
    queue.add(jsonArrayRequest)
}
