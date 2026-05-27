package com.example.mycityapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categories: List<CityCategory>,
    onCategoryClick: (CityCategory) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("My City") }) }
    ) { paddingValues ->
        CategoryListContent(
            categories = categories,
            onCategoryClick = onCategoryClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
@Composable
fun CategoryListContent(
    categories: List<CityCategory>,
    onCategoryClick: (CityCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(categories) { category ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { onCategoryClick(category) }
            ) {
                Text(
                    text = stringResource(category.nameRes),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
