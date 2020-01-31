package com.example.makeyourownpizza.data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BestellingRespository(val app: Application) {

    val alleBestelling= MutableLiveData<List<Bestelling>>()
    private val bestellingDao = BestellingDatabase.getDatabase(app)
        .BestellingDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val data = bestellingDao.GetTheOrders()
            alleBestelling.postValue(data)
        }
    }
    suspend fun insert(bestelling: Bestelling){
        bestellingDao.insert(bestelling)
    }

    suspend fun delete(){
        bestellingDao.deleteAll()
    }

    suspend fun deleteBestelling(bestelling: Bestelling){
        bestellingDao.deleteBestelling(bestelling)
    }


}