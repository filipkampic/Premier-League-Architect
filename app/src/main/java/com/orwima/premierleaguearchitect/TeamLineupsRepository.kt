package com.orwima.premierleaguearchitect

class TeamLineupsRepository {
    fun getTeamLineupsByName(teamName: String): List<Pair<String, Int>> {
        return when(teamName) {
            "Arsenal" -> listOf(
                Pair("Best Arsenal 11", R.drawable.arsenal),
                Pair("Lineup vs Spurs", R.drawable.arsenal),
            )
            "Chelsea" -> listOf(
                Pair("Best Chelsea 11", R.drawable.chelsea),
                Pair("Lineup vs Liverpool", R.drawable.chelsea),
            )
            else -> emptyList()
        }
    }
}