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
            TeamLineups(navController = navController, teamName = teamName, firestoreRepository = FirestoreRepository())
        }
        composable(
            route = "lineup_builder/{teamName}/{teamLogo}/{teamJersey}/{teamGoalkeeperJersey}",
            arguments = listOf(
                navArgument("teamName") { type = NavType.StringType },
                navArgument("teamLogo") { type = NavType.IntType },
                navArgument("teamJersey") { type = NavType.IntType },
                navArgument("teamGoalkeeperJersey") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val teamName = backStackEntry.arguments?.getString("teamName") ?: ""
            val teamLogo = backStackEntry.arguments?.getInt("teamLogo") ?: 0
            val teamJersey = backStackEntry.arguments?.getInt("teamJersey") ?: 0
            val teamGoalkeeperJersey = backStackEntry.arguments?.getInt("teamGoalkeeperJersey") ?: 0
            LineupBuilder(teamName = teamName, teamLogo = teamLogo, teamJersey = teamJersey, teamGoalkeeperJersey = teamGoalkeeperJersey, onSave = { /* TO-DO: Save Lineup */}, onLeave = { navController.navigate("home") })
        }
    }
}