package com.example.makeyourownpizza.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeyourownpizza.data.Bestelling
import com.example.makeyourownpizza.data.BestellingRespository
import kotlinx.coroutines.launch

class SharedView(val app:Application) :AndroidViewModel(app) {


    private val respository =BestellingRespository(app)
    val alleBestellingen = respository.alleBestelling


    fun insert(bestelling: Bestelling) = viewModelScope.launch {
        respository.insert(bestelling)
    }

    fun delete() = viewModelScope.launch {
        respository.delete()
    }

    fun deleteBestelling(bestelling: Bestelling) = viewModelScope.launch {
        respository.deleteBestelling(bestelling)
    }


}