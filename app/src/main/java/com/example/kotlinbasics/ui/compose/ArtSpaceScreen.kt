package com.example.kotlinbasics.ui.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kotlinbasics.R

data class Artwork(
    @DrawableRes val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)

private val artworks = listOf(
    Artwork(R.drawable.art_1, "Прямоугольник с диагоналями", "Алексей Попов", "2026"),
    Artwork(R.drawable.art_2, "Синий круг и белый квадрат", "Алексей Попов", "2026"),
    Artwork(R.drawable.art_3, "Зелёный треугольник и жёлтый кружок", "Алексей Попов", "2026"),
    Artwork(R.drawable.art_4, "Жёлтый круг на сиреневых полосках", "Алексей Попов", "2026")
)

@Composable
fun ArtSpaceScreen(onBack: () -> Unit) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentArtwork = artworks[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .border(1.dp, Color.Gray)
        ) {
            Image(
                painter = painterResource(currentArtwork.imageRes),
                contentDescription = currentArtwork.title,
                modifier = Modifier.padding(16.dp),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentArtwork.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${currentArtwork.artist} (${currentArtwork.year})",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    currentIndex = when (currentIndex) {
                        0 -> artworks.lastIndex
                        else -> currentIndex - 1
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Назад")
            }

            Button(
                onClick = {
                    currentIndex = when (currentIndex) {
                        artworks.lastIndex -> 0
                        else -> currentIndex + 1
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Вперёд")
            }
        }
    }
}
