package com.example.makeyourownpizza.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BestellingDao{

    @Query("SELECT * from Bestelling_DB ORDER BY id ASC")
    fun GetTheOrders(): List<Bestelling>

    @Insert
    suspend fun insert(bestelling: Bestelling)

    @Query("DELETE FROM bestelling_db ")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteBestelling(bestelling: Bestelling)


}