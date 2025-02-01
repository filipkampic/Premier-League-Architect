package com.orwima.premierleaguearchitect

data class Player(
    val name: String = "",
    val image: String = "",
    val team: String = "",
    val positions: List<String> = emptyList()
)

data class Lineup(
    val team: String = "",
    val formation: String = "",
    val positions: Map<String, String> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis()
)
