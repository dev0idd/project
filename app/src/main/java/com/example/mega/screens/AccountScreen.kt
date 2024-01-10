package com.example.mega.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.mega.DataStoreManager
import com.example.mega.UserInfo
import kotlinx.coroutines.launch

@Composable
fun AccountScreen() {
    val context = LocalContext.current
    val state = DataStoreManager(context)
    val corutine = rememberCoroutineScope()

    val userInfo = remember {
        mutableStateOf(UserInfo(false, "", ""))
    }

    LaunchedEffect(key1 = true){
        state.getAuthInfo().collect{info ->
            userInfo.value = info
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {
            corutine.launch {
                userInfo.value.isLogin = false
                state.saveAuthInfo(
                    userInfo.value
                )
            }
        }) {
            Text(text = "Выйти", fontSize = 20.sp)
        }
    }
}