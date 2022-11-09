package com.moovim.nav

import com.moovim.R

sealed class NavigationItem(var route:String, var icon: Int, var title: String) {
    object Home: NavigationItem("home", R.drawable.ic_home, "Inicio")
    object Search: NavigationItem("search", R.drawable.ic_search, "Buscar")
    object Routines: NavigationItem("routines", R.drawable.ic_routines, "Mis Rutinas")
}