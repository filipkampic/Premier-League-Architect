package com.orwima.premierleaguearchitect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun CreateLineupMenuPreview() {
    CreateLineupMenu(navController = rememberNavController())
}

@Composable
fun CreateLineupMenu(
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

    val selectedTeam = remember { mutableStateOf<Pair<String, Int>?>(null) }

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

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "PICK YOUR CLUB",
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
                                .clickable { selectedTeam.value = team }
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

        if (selectedTeam.value != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { },
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Color(0XFF38003C), RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                        ) {
                            Text(
                                text = "X",
                                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                                fontSize = 20.sp,
                                color = Color(0XFF00FF85),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .clickable {
                                        selectedTeam.value = null
                                    }
                                    .padding(8.dp)
                            )

                            Text(
                                text = selectedTeam.value?.first ?: "",
                                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                                fontSize = 24.sp,
                                color = Color(0XFF00FF85),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )


                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(0XFF00FF85), RoundedCornerShape(10.dp))
                                .clickable {
                                    selectedTeam.value?.let { team ->
                                        navController.navigate("lineup_builder/${team.first}/${team.second}")
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "CONFIRM",
                                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                                fontSize = 20.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
