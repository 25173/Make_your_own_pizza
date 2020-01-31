package com.example.makeyourownpizza.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Bestelling::class],version = 2,exportSchema = false)
abstract class BestellingDatabase :RoomDatabase(){

    abstract fun BestellingDao():BestellingDao


    companion object {
        @Volatile
        private var INSTANCE: BestellingDatabase? = null

        fun getDatabase(
            context: Context
        ): BestellingDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BestellingDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}