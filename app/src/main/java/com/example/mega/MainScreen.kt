package com.example.mega

import NavGraph
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.navigation.compose.rememberNavController
import com.example.mega.bottom.navigation.NavigationBar
import com.example.mega.screens.LoginScreen
import kotlinx.coroutines.flow.Flow


@Composable
fun MainScreen() {
    val context = LocalContext.current
    val state = DataStoreManager(context)

    val navController = rememberNavController()

    val userInfo = remember {
        mutableStateOf(UserInfo(false, "", ""))
    }

    LaunchedEffect(key1 = true){
        state.getAuthInfo().collect{info ->
            userInfo.value = info
        }
    }

    if (!userInfo.value.isLogin){
        LoginScreen(state)
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar(navController)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding())
            ) {
                NavGraph(navController)
            }
        }
    }
}
