package edu.practico.seriesapp

sealed class Screens (val route : String) {
    object SeriesScreen : Screens("series")
    object DetailScreen : Screens("ShowDetailByEd/{id}") {
        fun createRoute(id: Int) : String {
            return "ShowDetailByEd/$id"
        }
    }

    object CharacterScreen: Screens("character/{id}") {
        fun createRoute(id: Int) : String {
            return "character/$id"
        }
    }

    object ActorScreen: Screens("actor/{id}") {
        fun createRoute(id: Int) : String {
            return "actor/$id"
        }
    }
}