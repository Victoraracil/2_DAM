package edu.victoraracil.prclase04.ui.navigation

sealed class NavScreens(val route: String) {
    object NavMainScreen : NavScreens("main")
    object NavDetailScreen : NavScreens("detail")
    object NavAboutScreen : NavScreens("about")
    object NavSettingsScreen : NavScreens("settings")
}