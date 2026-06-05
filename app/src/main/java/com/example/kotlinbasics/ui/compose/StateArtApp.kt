package com.example.kotlinbasics.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private enum class AppScreen {
    Menu, TipCalculator, ArtSpace
}

@Composable
fun StateArtApp() {
    var currentScreen by remember { mutableStateOf(AppScreen.Menu) }

    when (currentScreen) {
        AppScreen.Menu -> MainMenuScreen(
            onTipClick = { currentScreen = AppScreen.TipCalculator },
            onArtClick = { currentScreen = AppScreen.ArtSpace }
        )
        AppScreen.TipCalculator -> TipCalculatorScreen(
            onBack = { currentScreen = AppScreen.Menu }
        )
        AppScreen.ArtSpace -> ArtSpaceScreen(
            onBack = { currentScreen = AppScreen.Menu }
        )
    }
}

@Composable
fun MainMenuScreen(
    onTipClick: () -> Unit,
    onArtClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onTipClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Калькулятор чаевых", fontSize = 20.sp)
        }

        Button(
            onClick = onArtClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Картинки", fontSize = 20.sp)
        }
    }
}
