package com.example.mega.bottom.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBar(
    navController: NavController
) {
    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
    )
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination
        listItems.forEach { item ->
            var isSelected = currentRoute?.route == item.route

            BottomNavigationItem(
                selected = isSelected,
                alwaysShowLabel = false,
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon",
                        tint = if (isSelected) Color.Red else Color.Gray
                        )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp,
                        color = if (isSelected) Color.Red else Color.Gray
                    )
                }
            )
        }
    }
}