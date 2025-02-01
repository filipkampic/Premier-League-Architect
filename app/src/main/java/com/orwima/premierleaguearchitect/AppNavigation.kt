package com.orwima.premierleaguearchitect

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(
    viewModel: LineupsViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController = navController) }
        composable("team_selection") { CreateLineupMenu(navController = navController) }
        composable("saved_lineups") { SavedLineupsMenu(navController = navController, viewModel = viewModel) }
        composable("lineups_by_team") { LineupsByTeamMenu(navController = navController) }
        composable(
            route = "team_lineups/{teamName}",
            arguments = listOf(navArgument("teamName") { type = NavType.StringType })
        ) { backStackEntry ->
            val teamName = backStackEntry.arguments?.getString("teamName") ?: ""
            TeamLineups(navController = navController, teamName = teamName, viewModel = viewModel)
        }
        composable(
            route = "lineup_builder/{teamName}/{teamLogo}/{teamJersey}/{teamGoalkeeperJersey}/{lineupName}",
            arguments = listOf(
                navArgument("teamName") { type = NavType.StringType },
                navArgument("teamLogo") { type = NavType.IntType },
                navArgument("teamJersey") { type = NavType.IntType },
                navArgument("teamGoalkeeperJersey") { type = NavType.IntType },
                navArgument("lineupName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val teamName = backStackEntry.arguments?.getString("teamName") ?: ""
            val teamLogo = backStackEntry.arguments?.getInt("teamLogo") ?: 0
            val teamJersey = backStackEntry.arguments?.getInt("teamJersey") ?: 0
            val teamGoalkeeperJersey = backStackEntry.arguments?.getInt("teamGoalkeeperJersey") ?: 0
            val lineupName = backStackEntry.arguments?.getString("lineupName") ?: "LINEUP NAME"

            LineupBuilder(
                navController = navController,
                viewModel = viewModel,
                teamName = teamName,
                teamLogo = teamLogo,
                teamJersey = teamJersey,
                teamGoalkeeperJersey = teamGoalkeeperJersey,
                lineupName = lineupName,
                onSave = { navController.navigate("team_lineups/$teamName") },
                onLeave = { navController.navigate("home") }
            )
        }
    }
}