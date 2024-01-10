package com.example.mega.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.mega.baseUrl
import com.example.mega.components.AddActionToBagDialog
import com.example.mega.components.CompanyShareCard
import com.example.mega.components.CompanyShareModel
import org.json.JSONException

@Composable
fun ListPage() {
    val data = remember {
        mutableStateListOf<CompanyShareModel>()
    }

    val selectedAction = remember {
        mutableStateOf<CompanyShareModel?>(null)
    }

    val ctx = LocalContext.current
    Log.d("MyLog", data.toString())

    getActionsList(data, ctx)

    if(selectedAction.value != null){
        AddActionToBagDialog(action = selectedAction.value!!, onClose={selectedAction.value = null})
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            data
        ) { _, item ->
            CompanyShareCard(companyShareModel = item, onSelect = {
                selectedAction.value = item
            })
        }
    }
}

fun getActionsList(state: MutableList<CompanyShareModel>, context: Context) {
    val url = "$baseUrl/list"
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
                    val ticker: String = responseObj.getString("ticker")
                    val isin: String = responseObj.getString("isin")
                    val currency: String = responseObj.getString("currency")
                    val lotSize = responseObj.getString("lotSize")
                    val exchangeShowName: String = responseObj.getString("exchangeShowName")
                    val showName: String = responseObj.getString("showName")
                    val logoName: String = responseObj.getString("logoName")
                    val color: String = responseObj.getString("color")
                    val textColor: String = responseObj.getString("textColor")
                    val countryOfRiskBriefName: String =
                        responseObj.getString("countryOfRiskBriefName")
                    val brand: String = responseObj.getString("brand")
                    val price: Double = responseObj.getDouble("price")
                    val isFavorite: Boolean = responseObj.getBoolean("isFavorite")
                    val riskCategory = responseObj.getString("riskCategory")
                    val depository: String = responseObj.getString("depository")
                    val depoAccountSection: String = responseObj.getString("depoAccountSection")
                    val exchangeLogoUrl: String = responseObj.getString("exchangeLogoUrl")
                    val countryOfRiskLogoURL = responseObj.getString("countryOfRiskLogoUrl")

                    val item = CompanyShareModel(
                        id,
                        ticker,
                        isin,
                        currency,
                        lotSize,
                        exchangeShowName,
                        exchangeLogoUrl,
                        showName,
                        logoName,
                        color,
                        textColor,
                        countryOfRiskBriefName,
                        countryOfRiskLogoURL,
                        brand,
                        price,
                        isFavorite,
                        riskCategory,
                        depository,
                        depoAccountSection
                    )
                    Log.d("MyLog", item.toString())

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