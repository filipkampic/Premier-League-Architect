package com.orwima.premierleaguearchitect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
@Preview(showBackground = true)
fun LineupsByTeamMenuPreview() {
    LineupsByTeamMenu(navController = rememberNavController())
}

@Composable
fun LineupsByTeamMenu(
    navController: NavController
) {
    val teams = listOf(
        Pair("Arsenal", R.drawable.arsenal),
        Pair("Aston Villa", R.drawable.aston_villa),
        Pair("Bournemouth", R.drawable.bournemouth),
        Pair("Brentford", R.drawable.brentford),
        Pair("Brighton", R.drawable.brighton),
        Pair("Chelsea", R.drawable.chelsea),
        Pair("Crystal Palace", R.drawable.crystal_palace),
        Pair("Everton", R.drawable.everton),
        Pair("Fulham", R.drawable.fulham),
        Pair("Ipswich Town", R.drawable.ipswich_town),
        Pair("Leicester City", R.drawable.leicester_city),
        Pair("Liverpool", R.drawable.liverpool),
        Pair("Man City", R.drawable.man_city),
        Pair("Man United", R.drawable.man_united),
        Pair("Newcastle", R.drawable.newcastle_united),
        Pair("Nottingham Forest", R.drawable.nottingham_forest),
        Pair("Southampton", R.drawable.southampton),
        Pair("Tottenham", R.drawable.tottenham),
        Pair("West Ham", R.drawable.west_ham),
        Pair("Wolves", R.drawable.wolves)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF252431))
    ) {
        Image(
            painter = painterResource(id = R.drawable.pl_bg),
            contentDescription = "Premier League Logo Background",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.4f)
                .scale(3.2f)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = "Back",
            tint = Color(0XFFF40C5C),
            modifier = Modifier
                .size(64.dp)
                .padding(top = 16.dp)
                .align(Alignment.TopStart)
                .clickable {
                    navController.navigate("home")
                }
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "LINEUPS BY TEAM",
                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                fontSize = 48.sp,
                color = Color(0XFF00FF85),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, top = 64.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .weight(1f)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    items(teams) { team ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { navController.navigate("team_lineups/${team.first}") }
                        ) {
                            Image(
                                painter = painterResource(id = team.second),
                                contentDescription = team.first,
                                modifier = Modifier.size(70.dp)
                            )
                            Text(
                                text = team.first,
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                fontSize = 10.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}