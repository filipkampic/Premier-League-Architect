package com.orwima.premierleaguearchitect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.exp

@Composable
@Preview(showBackground = true)
fun LineupBuilderPreview() {
    LineupBuilder("Arsenal", R.drawable.arsenal, listOf("4-3-3"), {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineupBuilder(
    teamName: String,
    teamLogo: Int,
    availableFormations: List<String>,
    onSave: () -> Unit,
    onLeave: () -> Unit
) {
    var selectedFormation by remember { mutableStateOf(availableFormations.first()) }
    var lineupName by remember { mutableStateOf("LINEUP NAME") }
    var isNameFocused by remember { mutableStateOf(false) }
    val positions = getFormationPositions(selectedFormation)

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
                        if (focusState.isFocused && !isNameFocused) {
                            lineupName = ""
                            isNameFocused = true
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
                    .height(400.dp)
            ) {
                positions.forEach { (position, coordinates) ->
                    Box(
                        modifier = Modifier
                            .offset(
                                x = coordinates.first.dp,
                                y = coordinates.second.dp
                            )
                            .size(48.dp)
                            .clickable {
                                // Handle position click
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.player_jersey),
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
                            .width(90.dp)
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
                            .width(90.dp)

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
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(120.dp, 26.dp)
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
                        .height(16.dp),
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

fun getFormationPositions(formation: String): Map<String, Pair<Int, Int>> {
    return when(formation) {
        "4-3-3" -> mapOf(
            "GK" to Pair(0, 300),
            "LB" to Pair(-120, 220),
            "LCB" to Pair(-50, 250),
            "RCB" to Pair(50, 250),
            "RB" to Pair(120, 220),
            "LCM" to Pair(-75, 150),
            "CDM" to Pair(0, 175),
            "RCM" to Pair(75, 150),
            "LW" to Pair(-100, 75),
            "ST" to Pair(0, 50),
            "RW" to Pair(100 , 75)
        )
        else -> emptyMap()
    }
}
