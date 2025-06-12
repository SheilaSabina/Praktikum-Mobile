package com.example.modul5.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.modul5.data.local.dao.GunungDao
import com.example.modul5.data.local.entity.GunungEntity

@Database(entities = [GunungEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gunungDao(): GunungDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gunung_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
