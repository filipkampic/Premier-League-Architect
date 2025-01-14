package com.orwima.premierleaguearchitect

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController = navController) }
        composable("team_selection") { CreateLineupMenu(navController = navController) }
        composable("saved_lineups") { SavedLineupsMenu(navController = navController) }
        composable("lineups_by_team") { LineupsByTeamMenu(navController = navController) }

        composable(
            route = "team_lineups/{teamName}",
            arguments = listOf(navArgument("teamName") { type = NavType.StringType })
        ) { backStackEntry ->
            val teamName = backStackEntry.arguments?.getString("teamName") ?: ""
           // val teamLineups = getTeamLineupsByName(teamName)
            //TeamLineups(navController = navController, teamName = teamName, teamLineups = teamLineups)
        }
    }
}