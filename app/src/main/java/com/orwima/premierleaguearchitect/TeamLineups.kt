package com.orwima.premierleaguearchitect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TeamLineups(
    navController: NavController,
    teamName: String,
    viewModel: LineupsViewModel
) {
    val lineups by viewModel.lineups.collectAsState()
    var sortedLineups by remember { mutableStateOf(emptyList<Pair<String, Lineup>>()) }

    val sortOptions = listOf("Created (Newest)", "Created (Oldest)", "Name (A-Z)", "Name (Z-A)")
    val selectedSortOption = remember { mutableStateOf(sortOptions[0]) }
    val isDropdownExpanded = remember { mutableStateOf(false) }

    LaunchedEffect(lineups) {
        sortedLineups = lineups.sortedByDescending { it.second.timestamp }
    }

    LaunchedEffect(teamName) {
        viewModel.fetchTeamLineups(teamName)
    }

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
                    navController.navigate("lineups_by_team")
                }
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "$teamName LINEUPS",
                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                fontSize = 48.sp,
                color = Color(0XFF00FF85),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 64.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "SORT BY:",
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(140.dp, 26.dp)
                        .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                        .clickable { isDropdownExpanded.value = true },
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = selectedSortOption.value,
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    DropdownMenu(
                        expanded = isDropdownExpanded.value,
                        onDismissRequest = { isDropdownExpanded.value = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        sortOptions.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedSortOption.value = option
                                    isDropdownExpanded.value = false

                                    when(option) {
                                        "Created (Newest)" -> {
                                            sortedLineups = lineups.sortedByDescending { it.second.timestamp }
                                        }
                                        "Created (Oldest)" -> {
                                            sortedLineups = lineups.sortedBy { it.second.timestamp }
                                        }
                                        "Name (A-Z)" -> {
                                            sortedLineups = lineups.sortedBy { it.first }
                                        }
                                        "Name (Z-A)" -> {
                                            sortedLineups = lineups.sortedByDescending { it.first }
                                        }
                                    }
                                },
                                text = {
                                    Text(
                                        text = option,
                                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            )
                        }
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top= 8.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxHeight(0.9f)
            ) {
                items(sortedLineups) { lineup ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0XFF38003C),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                            .clickable {
                                val teamLogo = getTeamLogo(lineup.second.team)
                                val teamJersey = getTeamJersey(lineup.second.team)
                                val teamGoalkeeperJersey = getTeamGoalkeeperJersey(lineup.second.team)
                                val lineupName = lineup.first

                                navController.navigate("lineup_builder/${lineup.second.team}/$teamLogo/$teamJersey/$teamGoalkeeperJersey/$lineupName")
                            }
                    ) {
                        Image(
                            painter = painterResource(id = getTeamLogo(lineup.second.team)),
                            contentDescription = lineup.first,
                            modifier = Modifier
                                .size(56.dp)
                        )

                        Text(
                            text = lineup.first.uppercase(),
                            fontFamily = FontFamily(Font(R.font.paytoneone_regular)),
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
