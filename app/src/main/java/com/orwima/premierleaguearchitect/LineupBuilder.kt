package com.orwima.premierleaguearchitect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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

    var lineupName by remember { mutableStateOf("LINEUP NAME") }
    var isNameFocused by remember { mutableStateOf(false) }

    var responsivePositions by remember { mutableStateOf<Map<String, Pair<Dp, Dp>>>(emptyMap()) }
    val positions = getFormationPositions(selectedFormation)

    val density = LocalDensity.current
    var containerWidth by remember { mutableStateOf(0) }
    var containerHeight by remember { mutableStateOf(0) }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

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
                    .aspectRatio(1f) // Ensure the box is square
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
                    val iconSize = with(density) { (containerWidth * 0.125f).toDp() }

                    Box(
                        modifier = Modifier
                            .offset(
                                x = coordinates.first,
                                y = coordinates.second
                            )
                            .size(iconSize)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }

                            ) {
                                // Handle position click
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                id = if (position == "GK") teamGoalkeeperJersey else teamJersey),
                            contentDescription = position
                        )
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
                    onFormationSelected = { selectedFormation = it }
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
    }
}

@Composable
fun DropdownMenuComponent(
    selectedFormation: String,
    availableFormations: List<String>,
    onFormationSelected: (String) -> Unit
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
                        onFormationSelected(formation)
                    }
                )
            }
        }
    }
}

fun getFormationPositions(formation: String): Map<String, Pair<Float, Float>> {
    return when(formation) {
        "4-3-3" -> mapOf(
            "GK" to Pair(0.44f, 0.82f),
            "LB" to Pair(0.14f, 0.62f),
            "LCB" to Pair(0.29f, 0.72f),
            "RCB" to Pair(0.59f, 0.72f),
            "RB" to Pair(0.74f, 0.62f),
            "LCM" to Pair(0.24f, 0.42f),
            "CDM" to Pair(0.44f, 0.52f),
            "RCM" to Pair(0.64f, 0.42f),
            "LW" to Pair(0.19f, 0.17f),
            "ST" to Pair(0.44f, 0.12f),
            "RW" to Pair(0.69f, 0.17f)
        )
        "4-2-3-1" -> mapOf(
            "GK" to Pair(0.44f, 0.82f),
            "LB" to Pair(0.14f, 0.62f),
            "LCB" to Pair(0.29f, 0.72f),
            "RCB" to Pair(0.59f, 0.72f),
            "RB" to Pair(0.74f, 0.62f),
            "LDM" to Pair(0.29f, 0.52f),
            "RDM" to Pair(0.59f, 0.52f),
            "LM" to Pair(0.19f, 0.32f),
            "CAM" to Pair(0.44f, 0.32f),
            "RM" to Pair(0.69f, 0.32f),
            "ST" to Pair(0.44f, 0.12f)
        )
        "4-4-2" -> mapOf(
            "GK" to Pair(0.44f, 0.82f),
            "LB" to Pair(0.14f, 0.62f),
            "LCB" to Pair(0.29f, 0.72f),
            "RCB" to Pair(0.59f, 0.72f),
            "RB" to Pair(0.74f, 0.62f),
            "LM" to Pair(0.19f, 0.42f),
            "LCM" to Pair(0.34f, 0.47f),
            "RCM" to Pair(0.54f, 0.47f),
            "RM" to Pair(0.69f, 0.42f),
            "LST" to Pair(0.34f, 0.22f),
            "RST" to Pair(0.54f, 0.22f)
        )
        "3-4-2-1" -> mapOf(
            "GK" to Pair(0.44f, 0.82f),
            "LCB" to Pair(0.24f, 0.69f),
            "CB" to Pair(0.44f, 0.69f),
            "RCB" to Pair(0.64f, 0.69f),
            "LM" to Pair(0.19f, 0.47f),
            "LDM" to Pair(0.34f, 0.52f),
            "RDM" to Pair(0.54f, 0.52f),
            "RM" to Pair(0.69f, 0.47f),
            "LAM" to Pair(0.29f, 0.27f),
            "RAM" to Pair(0.59f, 0.27f),
            "ST" to Pair(0.44f, 0.12f)
        )
        "3-5-2" -> mapOf(
            "GK" to Pair(0.44f, 0.82f),
            "LCB" to Pair(0.24f, 0.69f),
            "CB" to Pair(0.44f, 0.69f),
            "RCB" to Pair(0.64f, 0.69f),
            "LM" to Pair(0.18f, 0.42f),
            "LDM" to Pair(0.33f, 0.47f),
            "CDM" to Pair(0.44f, 0.52f),
            "RDM" to Pair(0.55f, 0.47f),
            "RM" to Pair(0.70f, 0.42f),
            "LST" to Pair(0.34f, 0.22f),
            "RST" to Pair(0.54f, 0.22f)
        )
        "4-4-1-1" -> mapOf(
            "GK" to Pair(0.44f, 0.82f),
            "LB" to Pair(0.14f, 0.62f),
            "LCB" to Pair(0.29f, 0.72f),
            "RCB" to Pair(0.59f, 0.72f),
            "RB" to Pair(0.74f, 0.62f),
            "LM" to Pair(0.19f, 0.42f),
            "LCM" to Pair(0.34f, 0.47f),
            "RCM" to Pair(0.54f, 0.47f),
            "RM" to Pair(0.69f, 0.42f),
            "CAM" to Pair(0.44f, 0.27f),
            "ST" to Pair(0.44f, 0.12f)
        )
        else -> emptyMap()
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