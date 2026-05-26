package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    private val desserts = Datasource.dessertList


    private val _uiState = MutableStateFlow(
        DessertUiState(
            currentDessertPrice = desserts[0].price,
            currentDessertImageId = desserts[0].imageId
        )
    )
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()


    fun onDessertClicked() {
        _uiState.update { currentState ->
            val newDessertsSold = currentState.dessertsSold + 1
            val nextDessert = determineDessertToShow(newDessertsSold)

            currentState.copy(
                revenue = currentState.revenue + currentState.currentDessertPrice,
                dessertsSold = newDessertsSold,
                currentDessertPrice = nextDessert.price,
                currentDessertImageId = nextDessert.imageId
            )
        }
    }


    private fun determineDessertToShow(dessertsSold: Int): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                break
            }
        }
        return dessertToShow
    }
}