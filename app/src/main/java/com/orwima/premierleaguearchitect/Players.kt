package com.orwima.premierleaguearchitect

data class Player(
    val name: String,
    val image: Int,
    val team: String,
    val positions: List<String>
)

object PlayerRepository {
    val allPlayers = listOf(
        Player(name = "K. Havertz", image = R.drawable.havertz, team = "Arsenal", positions = listOf("ST", "CAM")),
        Player(name = "G. Jesus", image = R.drawable.g_jesus, team = "Arsenal", positions = listOf("ST")),
        Player(name = "G. Martinelli", image = R.drawable.martinelli, team = "Arsenal", positions = listOf("LW")),
        Player(name = "L. Trossard", image = R.drawable.trossard, team = "Arsenal", positions = listOf("LW")),
        Player(name = "B. Saka", image = R.drawable.saka, team = "Arsenal", positions = listOf("RW")),
        Player(name = "E. Nwaneri", image = R.drawable.nwaneri, team = "Arsenal", positions = listOf("CAM", "RW")),
        Player(name = "R. Sterling", image = R.drawable.sterling, team = "Arsenal", positions = listOf("LW", "RW")),
        Player(name = "M. Odegaard", image = R.drawable.odegaard, team = "Arsenal", positions = listOf("CAM", "CM")),
        Player(name = "D. Rice", image = R.drawable.rice, team = "Arsenal", positions = listOf("CDM", "CM")),
        Player(name = "W. Saliba", image = R.drawable.saliba, team = "Arsenal", positions = listOf("CB")),
        Player(name = "Gabriel M.", image = R.drawable.gabriel, team = "Arsenal", positions = listOf("CB")),
        Player(name = "B. White", image = R.drawable.white, team = "Arsenal", positions = listOf("RB", "CB")),
        Player(name = "J. Timber", image = R.drawable.timber, team = "Arsenal", positions = listOf("RB", "CB", "LB")),
        Player(name = "T. Partey", image = R.drawable.partey, team = "Arsenal", positions = listOf("CDM", "RB")),
        Player(name = "M. Merino", image = R.drawable.merino, team = "Arsenal", positions = listOf("CM", "CDM")),
        Player(name = "Jorginho", image = R.drawable.jorginho, team = "Arsenal", positions = listOf("CDM", "CM")),
        Player(name = "R. Calafiori", image = R.drawable.calafiori, team = "Arsenal", positions = listOf("LB", "CB")),
        Player(name = "O. Zinchenko", image = R.drawable.zinchenko, team = "Arsenal", positions = listOf("LB", "CM")),
        Player(name = "J. Kiwior", image = R.drawable.kiwior, team = "Arsenal", positions = listOf("CB", "LB")),
        Player(name = "K. Tierney", image = R.drawable.tierney, team = "Arsenal", positions = listOf("LB")),
        Player(name = "M. Lewis-Skelly", image = R.drawable.lewis_skelly, team = "Arsenal", positions = listOf("LB", "CM")),
        Player(name = "T. Tomiyasu", image = R.drawable.tomiyasu, team = "Arsenal", positions = listOf("RB", "LB", "CB")),
        Player(name = "D. Raya", image = R.drawable.raya, team = "Arsenal", positions = listOf("GK")),
        Player(name = "Neto", image = R.drawable.neto, team = "Arsenal", positions = listOf("GK")),
    )
}