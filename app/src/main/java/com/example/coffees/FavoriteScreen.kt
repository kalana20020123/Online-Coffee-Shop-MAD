package com.example.coffees

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Manages the favorite coffee items
object FavoriteManager {
    private val _favorites = mutableStateListOf<CoffeeItem>()
    val favorites: List<CoffeeItem> = _favorites

    // Adds a coffee item to favorites if not already added
    fun addToFavorites(coffee: CoffeeItem) {
        if (!_favorites.contains(coffee)) {
            _favorites.add(coffee)
        }
    }

    // Removes a coffee item from favorites
    fun removeFromFavorites(coffee: CoffeeItem) {
        _favorites.remove(coffee)
    }

    // Checks if a coffee item is in favorites
    fun isFavorite(coffee: CoffeeItem): Boolean {
        return _favorites.contains(coffee)
    }
}

@Composable
fun FavoriteScreen() {
    val customFont = FontFamily(Font(R.font.krona))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Screen title
        Text(
            text = "My Favor",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = customFont,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Show message if no favorites are added
        if (FavoriteManager.favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorites yet",
                    fontFamily = customFont,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            // Display the list of favorite items in a grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(FavoriteManager.favorites) { coffee ->
                    FavoriteCard(coffee = coffee)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteCard(coffee: CoffeeItem) {
    val customFont = FontFamily(Font(R.font.krona))

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            // Image section with a remove button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                Image(
                    painter = painterResource(id = coffee.image),
                    contentDescription = coffee.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { FavoriteManager.removeFromFavorites(coffee) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = "Remove from favorites",
                        tint = Color(0xFFB87333)
                    )
                }
            }

            // Text details section
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Coffee name
                Text(
                    text = coffee.name,
                    fontFamily = customFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Coffee description
                Text(
                    text = coffee.description,
                    fontFamily = customFont,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                // Price display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = coffee.price,
                        fontFamily = customFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
