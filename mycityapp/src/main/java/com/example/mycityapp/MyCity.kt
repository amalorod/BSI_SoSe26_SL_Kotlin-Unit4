package com.example.mycityapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items

data class Recommendation(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)

data class CityCategory(
    @StringRes val nameRes: Int,
    val recommendations: List<Recommendation>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCityApp(windowWidthSizeClass: WindowWidthSizeClass) {
    val categories = remember { LocalDataProvider.allCategories }

    var currentCategory by remember { mutableStateOf<CityCategory?>(null) }
    var currentRecommendation by remember { mutableStateOf<Recommendation?>(null) }

    // Tablet-Layout (Nebeneinander)
    if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("My City - Tablet") }) }
        ) { paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Spalte 1
                CategoryListContent(
                    categories = categories,
                    onCategoryClick = {
                        currentCategory = it
                        currentRecommendation = null
                    },
                    modifier = Modifier.weight(1f)
                )

                // Spalte 2
                if (currentCategory != null) {
                    RecommendationListContent(
                        category = currentCategory!!,
                        onRecommendationClick = { currentRecommendation = it },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    // Platzhalter, wenn noch keine Kategorie gewählt wurde
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text("Wähle eine Kategorie aus", color = MaterialTheme.colorScheme.outline)
                    }
                }

                if (currentRecommendation != null) {
                    DetailScreenContent(
                        recommendation = currentRecommendation!!,
                        modifier = Modifier.weight(1.5f)
                    )
                } else {
                    // Platzhalter, wenn noch kein Ort gewählt wurde
                    Box(modifier = Modifier.weight(1.5f), contentAlignment = Alignment.Center) {
                        Text("Wähle einen Ort aus", color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
        }
    }

    // Normales Smartphone-Layout
    else {
        when {
            currentRecommendation != null -> {
                DetailScreen(recommendation = currentRecommendation!!, onBack = { currentRecommendation = null })
            }
            currentCategory != null -> {
                RecommendationScreen(category = currentCategory!!, onRecommendationClick = { currentRecommendation = it }, onBack = { currentCategory = null })
            }
            else -> {
                CategoryScreen(categories = categories, onCategoryClick = { currentCategory = it })
            }
        }
    }
}

