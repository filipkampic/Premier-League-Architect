package com.orwima.premierleaguearchitect

fun getTeamLogo(teamName: String): Int {
    return when(teamName) {
        "Arsenal" -> R.drawable.arsenal
        "Aston Villa" -> R.drawable.aston_villa
        "Bournemouth" -> R.drawable.bournemouth
        "Brentford" -> R.drawable.brentford
        "Brighton" -> R.drawable.brighton
        "Chelsea" -> R.drawable.chelsea
        "Crystal Palace" -> R.drawable.crystal_palace
        "Everton" -> R.drawable.everton
        "Fulham" -> R.drawable.fulham
        "Ipswich Town" -> R.drawable.ipswich_town
        "Leicester City" -> R.drawable.leicester_city
        "Liverpool" -> R.drawable.liverpool
        "Man City" -> R.drawable.man_city
        "Man United" -> R.drawable.man_united
        "Newcastle United" -> R.drawable.newcastle_united
        "Nottingham Forest" -> R.drawable.nottingham_forest
        "Southampton" -> R.drawable.southampton
        "Tottenham Hotspur" -> R.drawable.tottenham
        "West Ham United" -> R.drawable.west_ham
        "Wolverhampton Wanderers" -> R.drawable.wolves
        else -> R.drawable.app_logo
    }
}

fun getTeamJersey(teamName: String): Int {
    return when(teamName) {
        "Arsenal" -> R.drawable.jersey_arsenal
        "Aston Villa" -> R.drawable.jersey_avilla
        "Bournemouth" -> R.drawable.jersey_bournemouth
        "Brentford" -> R.drawable.jersey_brentford
        "Brighton" -> R.drawable.jersey_brighton
        "Chelsea" -> R.drawable.jersey_chelsea
        "Crystal Palace" -> R.drawable.jersey_palace
        "Everton" -> R.drawable.jersey_everton
        "Fulham" -> R.drawable.jersey_fulham
        "Ipswich Town" -> R.drawable.jersey_ipswich
        "Leicester City" -> R.drawable.jersey_leicester
        "Liverpool" -> R.drawable.jersey_liverpool
        "Man City" -> R.drawable.jersey_mancity
        "Man United" -> R.drawable.jersey_manunited
        "Newcastle United" -> R.drawable.jersey_newcastle
        "Nottingham Forest" -> R.drawable.jersey_forest
        "Southampton" -> R.drawable.jersey_southampton
        "Tottenham Hotspur" -> R.drawable.jersey_spurs
        "West Ham United" -> R.drawable.jersey_westham
        "Wolverhampton Wanderers" -> R.drawable.jersey_wolves
        else -> R.drawable.jersey_noteam

    }
}

fun getTeamGoalkeeperJersey(teamName: String): Int {
    return when(teamName) {
        "Arsenal" -> R.drawable.goalkeeper_arsenal
        "Aston Villa" -> R.drawable.goalkeeper_avilla
        "Bournemouth" -> R.drawable.goalkeeper_bournemouth
        "Brentford" -> R.drawable.goalkeeper_brentford
        "Brighton" -> R.drawable.goalkeeper_brighton
        "Chelsea" -> R.drawable.goalkeeper_chelsea
        "Crystal Palace" -> R.drawable.goalkeeper_cpalace
        "Everton" -> R.drawable.goalkeeper_everton
        "Fulham" -> R.drawable.goalkeeper_fulham
        "Ipswich Town" -> R.drawable.goalkeeper_ipswich
        "Leicester City" -> R.drawable.goalkeeper_leicester
        "Liverpool" -> R.drawable.goalkeeper_liverpool
        "Man City" -> R.drawable.goalkeeper_mancity
        "Man United" -> R.drawable.goalkeeper_manunited
        "Newcastle United" -> R.drawable.goalkeeper_newcastle
        "Nottingham Forest" -> R.drawable.goalkeeper_forest
        "Southampton" -> R.drawable.goalkeeper_southampton
        "Tottenham Hotspur" -> R.drawable.goalkeeper_spurs
        "West Ham United" -> R.drawable.goalkeeper_westham
        "Wolverhampton Wanderers" -> R.drawable.goalkeeper_wolves
        else -> R.drawable.jersey_bournemouth
    }
}