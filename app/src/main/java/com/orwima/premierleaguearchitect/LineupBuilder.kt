package com.orwima.premierleaguearchitect

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orwima.premierleaguearchitect.PlayerRepository.allPlayers

@Composable
@Preview(showBackground = true)
fun LineupBuilderPreview() {
    LineupBuilder("Bournemouth", R.drawable.bournemouth, R.drawable.jersey_bournemouth, R.drawable.goalkeeper_bournemouth, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineupBuilder(
    teamName: String,
    teamLogo: Int,
    teamJersey: Int,
    teamGoalkeeperJersey: Int,
    onSave: () -> Unit,
    onLeave: () -> Unit
) {
    val availableFormations = listOf("4-3-3", "4-2-3-1", "4-4-2", "3-4-2-1", "3-5-2", "4-4-1-1")
    var selectedFormation by remember { mutableStateOf(availableFormations.first()) }
    var clickedPosition by remember { mutableStateOf<String?>(null) }
    var isSidebarVisible by remember { mutableStateOf(false) }

    var lineupName by remember { mutableStateOf("LINEUP NAME") }
    var isNameFocused by remember { mutableStateOf(false) }

    val positions = getFormationPositions(selectedFormation)
    var responsivePositions by remember { mutableStateOf<Map<String, Pair<Dp, Dp>>>(emptyMap()) }

    val density = LocalDensity.current
    var containerWidth by remember { mutableStateOf(0) }
    var containerHeight by remember { mutableStateOf(0) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val playersByPosition = allPlayers
        .filter { it.team == teamName }
        .flatMap { player ->
            player.positions.map { position -> position to player }
        }
        .groupBy(
            keySelector = { (position, _) -> position },
            valueTransform = { (_, player) -> player }
        )

    val positionToPlayer = remember { mutableStateOf(mutableMapOf<String, String>()) }
    val playerToPosition = remember { mutableStateOf(mutableMapOf<String, String>()) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0XFF252431))
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
        Image(
            painter = painterResource(id = R.drawable.pitch),
            contentDescription = "Pitch",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = teamName.uppercase(),
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                color = Color(0XFF00FF85),
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            Image(
                painter = painterResource(id = teamLogo),
                contentDescription = "$teamName Logo",
                modifier = Modifier.size(80.dp)
            )
            TextField(
                value = lineupName,
                onValueChange = { lineupName = it },
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.bebas_neue)),
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            if (!isNameFocused) {
                                lineupName = ""
                                isNameFocused = true
                            }
                        }
                    },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(2.dp)
                    .background(Color.White)
                    .padding(top = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .onGloballyPositioned { layoutCoordinates ->
                        containerWidth = layoutCoordinates.size.width
                        containerHeight = layoutCoordinates.size.height

                        responsivePositions = calculateResponsivePositions(
                            positions = positions,
                            containerWidth = containerWidth,
                            containerHeight = containerHeight,
                            density = density
                        )
                    }
            ) {
                responsivePositions.forEach { (position, coordinates) ->
                    val basePosition = position.takeWhile { it.isLetter() }
                    val availablePlayers = playersByPosition[basePosition]

                    val selectedPlayer = positionToPlayer.value[position]
                    val iconSize = with(density) { (containerWidth * 0.125f).toDp() }
                    val playerImage = availablePlayers?.find { it.name == selectedPlayer }?.image

                    Column(
                        modifier = Modifier
                            .offset(
                                x = coordinates.first,
                                y = coordinates.second
                            )
                            .width(iconSize * 1.5f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                clickedPosition = position
                                isSidebarVisible = true
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(iconSize),
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedPlayer != null) {
                                Image(
                                    painter = painterResource(
                                        id = playerImage ?: (if (basePosition == "GK") teamGoalkeeperJersey else teamJersey)
                                    ),
                                    contentDescription = selectedPlayer
                                )
                            } else {
                                Image(
                                    painter = painterResource(
                                        id = if (basePosition == "GK") teamGoalkeeperJersey else teamJersey
                                    ),
                                    contentDescription = position
                                )
                            }
                        }

                        selectedPlayer?.let {
                            Text(
                                text = it,
                                fontSize = (iconSize.value * 0.2).sp,
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                color = Color.White,
                                modifier = Modifier.padding(top = 4.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                DropdownMenuComponent(
                    selectedFormation = selectedFormation,
                    availableFormations = availableFormations,
                    onFormationSelected = { newFormation ->
                        selectedFormation = newFormation
                    },
                    onFormationChange = {
                        positionToPlayer.value.clear()
                        playerToPosition.value.clear()
                    }
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onSave,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF40C5C)),
                        modifier = Modifier
                            .width(screenWidth * 0.3f)
                    ) {
                        Text(
                            text = "SAVE",
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = onLeave,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults
                            .buttonColors(containerColor = Color.White),
                        modifier = Modifier
                            .width(screenWidth * 0.3f)
                    ) {
                        Text(
                            text = "LEAVE",
                            color = Color.Black
                        )
                    }
                }
            }
        }

        if (clickedPosition != null) {
            if (isSidebarVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isSidebarVisible = false
                        }
                )
            }
            AnimatedVisibility(
                visible = isSidebarVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 200)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 200)
                )
            ) {
                val basePosition = clickedPosition?.takeWhile { it.isLetter() } ?: ""
                val availablePlayers = playersByPosition[basePosition]

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(200.dp)
                            .align(Alignment.CenterEnd)
                            .background(color = Color(0xFF38003C))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                // Do nothing, prevent closing sidebar when is clicked
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .horizontalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Spacer(modifier = Modifier.height(24.dp))
                            availablePlayers?.forEach { player ->
                                val rowHeight = 56.dp
                                val isPlayerTaken = playerToPosition.value[player.name] != null
                                val isPlayerSelectedForCurrentPosition = positionToPlayer.value[clickedPosition] == player.name

                                Row(
                                    modifier = Modifier
                                        .width(200.dp)
                                        .background(
                                            color = if (isPlayerSelectedForCurrentPosition) Color.White
                                                    else if (isPlayerTaken) Color.Gray
                                                    else Color.Transparent,
                                            shape = RectangleShape
                                        )
                                        .clickable {
                                            val previousPosition = playerToPosition.value[player.name]
                                            val currentPlayer = positionToPlayer.value[clickedPosition]

                                            if (isPlayerSelectedForCurrentPosition) {
                                                positionToPlayer.value.remove(clickedPosition)
                                                playerToPosition.value.remove(player.name)
                                            } else {
                                                if (previousPosition != null) {
                                                    positionToPlayer.value.remove(previousPosition)
                                                    playerToPosition.value.remove(player.name)
                                                }
                                                positionToPlayer.value[clickedPosition!!] = player.name
                                                playerToPosition.value[player.name] = clickedPosition!!

                                                if (currentPlayer != null) {
                                                    playerToPosition.value.remove(currentPlayer)
                                                }
                                            }
                                            isSidebarVisible = false
                                        }
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = player.image),
                                        contentDescription = player.name,
                                        modifier = Modifier.size(rowHeight * 0.6f)
                                    )

                                    Text(
                                        text = player.name,
                                        fontSize = (rowHeight.value * 0.3).sp,
                                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                        color = if (positionToPlayer.value[clickedPosition] == player.name) Color.Black else Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getFormationPositions(formation: String): Map<String, Pair<Float, Float>> {
    return when(formation) {
        "4-3-3" -> mapOf(
            "GK" to Pair(0.41f, 0.82f),
            "LB" to Pair(0.11f, 0.62f),
            "CB1" to Pair(0.26f, 0.72f),
            "CB2" to Pair(0.56f, 0.72f),
            "RB" to Pair(0.71f, 0.62f),
            "CM1" to Pair(0.21f, 0.42f),
            "CDM" to Pair(0.41f, 0.52f),
            "CM2" to Pair(0.61f, 0.42f),
            "LW" to Pair(0.16f, 0.17f),
            "ST" to Pair(0.41f, 0.12f),
            "RW" to Pair(0.66f, 0.17f)
        )
        "4-2-3-1" -> mapOf(
            "GK" to Pair(0.41f, 0.82f),
            "LB" to Pair(0.11f, 0.62f),
            "CB1" to Pair(0.26f, 0.72f),
            "CB2" to Pair(0.56f, 0.72f),
            "RB" to Pair(0.71f, 0.62f),
            "CDM1" to Pair(0.26f, 0.52f),
            "CDM2" to Pair(0.56f, 0.52f),
            "LM" to Pair(0.16f, 0.32f),
            "CAM" to Pair(0.41f, 0.32f),
            "RM" to Pair(0.66f, 0.32f),
            "ST" to Pair(0.41f, 0.12f)
        )
        "4-4-2" -> mapOf(
            "GK" to Pair(0.41f, 0.82f),
            "LB" to Pair(0.11f, 0.62f),
            "CB1" to Pair(0.26f, 0.72f),
            "CB2" to Pair(0.56f, 0.72f),
            "RB" to Pair(0.71f, 0.62f),
            "LM" to Pair(0.16f, 0.42f),
            "CM1" to Pair(0.31f, 0.47f),
            "CM2" to Pair(0.51f, 0.47f),
            "RM" to Pair(0.66f, 0.42f),
            "ST1" to Pair(0.31f, 0.22f),
            "ST2" to Pair(0.51f, 0.22f)
        )
        "3-4-2-1" -> mapOf(
            "GK" to Pair(0.41f, 0.82f),
            "CB1" to Pair(0.21f, 0.64f),
            "CB2" to Pair(0.41f, 0.64f),
            "CB3" to Pair(0.61f, 0.64f),
            "LM" to Pair(0.16f, 0.45f),
            "CDM1" to Pair(0.31f, 0.50f),
            "CDM2" to Pair(0.51f, 0.50f),
            "RM" to Pair(0.66f, 0.45f),
            "CAM1" to Pair(0.26f, 0.27f),
            "CAM2" to Pair(0.56f, 0.27f),
            "ST" to Pair(0.41f, 0.12f)
        )
        "3-5-2" -> mapOf(
            "GK" to Pair(0.41f, 0.82f),
            "CB1" to Pair(0.21f, 0.64f),
            "CB2" to Pair(0.41f, 0.64f),
            "CB3" to Pair(0.61f, 0.64f),
            "LM" to Pair(0.15f, 0.40f),
            "CM1" to Pair(0.27f, 0.45f),
            "CDM" to Pair(0.41f, 0.50f),
            "CM2" to Pair(0.55f, 0.45f),
            "RM" to Pair(0.67f, 0.40f),
            "ST1" to Pair(0.31f, 0.22f),
            "ST2" to Pair(0.51f, 0.22f)
        )
        "4-4-1-1" -> mapOf(
            "GK" to Pair(0.41f, 0.82f),
            "LB" to Pair(0.11f, 0.62f),
            "CB1" to Pair(0.26f, 0.72f),
            "CB2" to Pair(0.56f, 0.72f),
            "RB" to Pair(0.71f, 0.62f),
            "LM" to Pair(0.16f, 0.42f),
            "CM1" to Pair(0.31f, 0.47f),
            "CM2" to Pair(0.51f, 0.47f),
            "RM" to Pair(0.66f, 0.42f),
            "CAM" to Pair(0.41f, 0.27f),
            "ST" to Pair(0.41f, 0.12f)
        )
        else -> emptyMap()
    }
}

@Composable
fun DropdownMenuComponent(
    selectedFormation: String,
    availableFormations: List<String>,
    onFormationSelected: (String) -> Unit,
    onFormationChange: () -> Unit
) {
    val screenWidth = LocalDensity.current.run { LocalConfiguration.current.screenWidthDp.dp }
    val dropdownWidth = screenWidth * 0.4f

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(dropdownWidth)
            .height(36.dp)
            .background(Color.White, shape = RoundedCornerShape(4.dp))
            .clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedFormation,
            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            availableFormations.forEach { formation ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    text = {
                        Text(
                            text = formation,
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    },
                    onClick = {
                        expanded = false
                        onFormationChange()
                        onFormationSelected(formation)
                    }
                )
            }
        }
    }
}

fun calculateResponsivePositions(
    positions: Map<String, Pair<Float, Float>>,
    containerWidth: Int,
    containerHeight: Int,
    density: Density
): Map<String, Pair<Dp, Dp>> {
    return positions.mapValues { (_, coordinates) ->
        with(density) {
            Pair(
                (coordinates.first * containerWidth).toDp(),
                (coordinates.second * containerHeight).toDp()
            )
        }
    }
}
