package com.orwima.premierleaguearchitect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import kotlin.math.exp

@Composable
@Preview(showBackground = true)
fun SavedLineupsMenuPreview(modifier: Modifier = Modifier) {
    SavedLineupsMenu(navController = rememberNavController())
}

@Composable
fun SavedLineupsMenu(
    navController: NavController
) {
    val lineups = listOf(
        Pair("Best Arsenal 11", R.drawable.arsenal),
        Pair("No Palmer", R.drawable.chelsea),
        Pair("Lineup vs Spurs", R.drawable.arsenal),
        Pair("Amorim style", R.drawable.man_united)
    )

    val sortOptions = listOf("Created (Newest)", "Created (Oldest)", "Name (A-Z)", "Name (Z-A)", "Club (A-Z)", "Club (Z-A)")
    val selectedSortOption = remember { mutableStateOf(sortOptions[0]) }
    val isDropdownExpanded = remember { mutableStateOf(false) }

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
                text = "SAVED LINEUPS",
                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                fontSize = 48.sp,
                color = Color(0XFF00FF85),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, top = 64.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SORT BY:",
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    fontSize = 14.sp,
                    color = Color.White
                )

                Box {
                    Text(
                        text = selectedSortOption.value,
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .clickable { isDropdownExpanded.value = true }
                            .background(Color.White)
                            .padding(8.dp)
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
        }
    }
}