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
fun SavedLineupsMenuPreview() {
    SavedLineupsMenu(navController = rememberNavController(), firestoreRepository = FirestoreRepository())
}

@Composable
fun SavedLineupsMenu(
    navController: NavController,
    firestoreRepository: FirestoreRepository = FirestoreRepository()
) {
    val lineups = remember { mutableStateOf<List<Pair<String, Lineup>>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    val sortOptions = listOf("Created (Newest)", "Created (Oldest)", "Name (A-Z)", "Name (Z-A)", "Club (A-Z)", "Club (Z-A)")
    val selectedSortOption = remember { mutableStateOf(sortOptions[0]) }
    val isDropdownExpanded = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        firestoreRepository.fetchAllLineups(
            onSuccess = { fetchedLineups ->
                lineups.value = fetchedLineups.sortedBy { it.second.timestamp }
                isLoading.value = false
            },
            onError = { e ->
                error.value = e.message
                isLoading.value = false
            }
        )
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
                    navController.navigate("home")
                }
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading.value) {
                Text(
                    text = "Loading...",
                    color = Color.White
                )
            } else if (error.value != null) {
                Text(
                    text = "Error: ${error.value}",
                    color = Color.Red
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "SAVED LINEUPS",
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
                        .background(
                            Color.White,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                        )
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

                                    when (option) {
                                        "Created (Newest)" -> {
                                            lineups.value = lineups.value.sortedByDescending { it.second.timestamp }
                                        }

                                        "Created (Oldest)" -> {
                                            lineups.value = lineups.value.sortedBy { it.second.timestamp }
                                        }

                                        "Name (A-Z)" -> {
                                            lineups.value = lineups.value.sortedBy { it.first }
                                        }

                                        "Name (Z-A)" -> {
                                            lineups.value =
                                                lineups.value.sortedByDescending { it.first }
                                        }

                                        "Club (A-Z)" -> {
                                            lineups.value =
                                                lineups.value.sortedBy { it.second.team }
                                        }

                                        "Club (Z-A)" -> {
                                            lineups.value =
                                                lineups.value.sortedByDescending { it.second.team }
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
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                items(lineups.value) { lineup ->
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
                            modifier = Modifier.size(56.dp)
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