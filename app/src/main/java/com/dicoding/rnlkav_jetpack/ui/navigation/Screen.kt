package com.dicoding.rnlkav_jetpack.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Detail : Screen("home/{culinaryId}") {
        fun createRoute(culinaryId: Long) = "home/$culinaryId"
    }
}
