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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
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
import kotlin.math.exp

@Composable
@Preview(showBackground = true)
fun LineupBuilderPreview() {
    LineupBuilder("Arsenal", R.drawable.arsenal, listOf("4-3-3"), {}, {})
}

@Composable
fun LineupBuilder(
    teamName: String,
    teamLogo: Int,
    availableFormations: List<String>,
    onSave: () -> Unit,
    onLeave: () -> Unit
) {
    var selectedFormation by remember { mutableStateOf(availableFormations.first()) }
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
            Text(
                text = "LINEUP NAME",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
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
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onSave,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF40C5C))
                ) {
                    Text(
                        text = "SAVE",
                        color = Color.White
                    )
                }
                Button(
                    onClick = onLeave,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
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

@Composable
fun DropdownMenuComponent(
    selectedFormation: String,
    availableFormations: List<String>,
    onFormationSelected: (String) -> Unit

) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        Button(
            onClick = { expanded = !expanded },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C)),
            modifier = Modifier.shadow(4.dp)
        ) {
            Text(
                text = selectedFormation,
                color = Color.White
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF2C2C2C))
        ) {
            availableFormations.forEach { formation ->
                DropdownMenuItem(
                    text = { Text(text = formation, color = Color.White) },
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
