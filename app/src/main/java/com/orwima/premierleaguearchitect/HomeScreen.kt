package com.orwima.premierleaguearchitect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

@Composable
fun HomeScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0XFF252431))
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

        Text(
            text = "PREMIER LEAGUE ARCHITECT",
            fontFamily = FontFamily(Font(R.font.bebas_neue)),
            fontSize = 56.sp,
            color = Color(0XFF00FF85),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HomeButton(text = "CREATE LINEUP") {
                navController.navigate("team_selection")
            }

            Spacer(modifier = Modifier.height(32.dp))

            HomeButton(text = "SAVED LINEUPS") {
                navController.navigate("saved_lineups")
            }

            Spacer(modifier = Modifier.height(32.dp))

            HomeButton(text = "LINEUPS BY TEAM") {
                navController.navigate("lineups_by_team")
            }
        }
    }
}

@Composable
fun HomeButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(72.dp)
            .background(Color.White, shape = RoundedCornerShape(30.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.bebas_neue)),
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
    }
}