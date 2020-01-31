package com.example.makeyourownpizza.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Bestelling_DB")
data class Bestelling(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "bestelling")
    var bestellingOrder: String = ""

    )