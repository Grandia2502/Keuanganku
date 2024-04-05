package org.d3if3096.mobpro1.navigation

sealed class Screen(val route: String) {
    // tidak menggunakan parameter data karena menggunakan versi android yang lama
    object Home : Screen("mainScreen")
    object About : Screen("aboutScreen")
}