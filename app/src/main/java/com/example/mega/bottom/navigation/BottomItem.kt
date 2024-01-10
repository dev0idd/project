package com.example.mega.bottom.navigation

import com.example.mega.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String){
    object Screen1: BottomItem("Акции", R.drawable.baseline_featured_play_list_24, "list")
    object Screen2: BottomItem("Портфели", R.drawable.bag, "bag")
    object Screen3: BottomItem("ЛК", R.drawable.person, "person")
}