package com.orwima.premierleaguearchitect

data class Player(
    val name: String,
    val image: Int,
    val team: String
)

object PlayerRepository {
    val allPlayers = listOf(
        Player(name = "K. Havertz", image = R.drawable.havertz, team = "Arsenal"),
        Player(name = "G. Jesus", image = R.drawable.g_jesus, team = "Arsenal"),
        Player(name = "R. Sterling", image = R.drawable.sterling, team = "Arsenal"),
        Player(name = "B. Saka", image = R.drawable.saka, team = "Arsenal"),
        Player(name = "L. Trossard", image = R.drawable.trossard, team = "Arsenal"),
        Player(name = "G. Martinelli", image = R.drawable.martinelli, team = "Arsenal"),
        Player(name = "E. Nwaneri", image = R.drawable.nwaneri, team = "Arsenal"),
        Player(name = "M. Odegaard", image = R.drawable.odegaard, team = "Arsenal"),
        Player(name = "M. Merino", image = R.drawable.merino, team = "Arsenal"),
        Player(name = "D. Rice", image = R.drawable.rice, team = "Arsenal"),
        Player(name = "Jorginho", image = R.drawable.jorginho, team = "Arsenal"),
        Player(name = "T. Partey", image = R.drawable.partey, team = "Arsenal"),
        Player(name = "T. Tomiyasu", image = R.drawable.tomiyasu, team = "Arsenal"),
        Player(name = "J. Timber", image = R.drawable.timber, team = "Arsenal"),
        Player(name = "B. White", image = R.drawable.white, team = "Arsenal"),
        Player(name = "M. Lewis-Skelly", image = R.drawable.lewis_skelly, team = "Arsenal"),
        Player(name = "K. Tierney", image = R.drawable.tierney, team = "Arsenal"),
        Player(name = "O. Zinchenko", image = R.drawable.zinchenko, team = "Arsenal"),
        Player(name = "R. Calafiori", image = R.drawable.calafiori, team = "Arsenal"),
        Player(name = "J. Kiwior", image = R.drawable.kiwior, team = "Arsenal"),
        Player(name = "Gabriel M.", image = R.drawable.gabriel, team = "Arsenal"),
        Player(name = "W. Saliba", image = R.drawable.saliba, team = "Arsenal"),
        Player(name = "Neto", image = R.drawable.neto, team = "Arsenal"),
        Player(name = "D. Raya", image = R.drawable.raya, team = "Arsenal"),
    )
}