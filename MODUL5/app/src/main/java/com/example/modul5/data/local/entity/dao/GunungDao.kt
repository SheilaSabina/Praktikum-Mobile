package com.example.modul5.data.local.dao

import androidx.room.*
import com.example.modul5.data.local.entity.GunungEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GunungDao {

    @Query("SELECT * FROM gunung")
    fun getAllGunung(): Flow<List<GunungEntity>>

    @Query("SELECT * FROM gunung")
    suspend fun getAllGunungOnce(): List<GunungEntity>

    @Query("SELECT * FROM gunung WHERE isFavorite = 1")
    suspend fun getAllFavoriteGunung(): List<GunungEntity>

    @Query("SELECT * FROM gunung WHERE isFavorite = 1")
    fun getAllFavoriteGunungFlow(): Flow<List<GunungEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGunungList(gunungList: List<GunungEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGunung(gunung: GunungEntity)

    @Query("DELETE FROM gunung")
    suspend fun clearGunung()

    @Delete
    suspend fun delete(gunung: GunungEntity)

    @Update
    suspend fun update(gunung: GunungEntity)

    @Query("UPDATE gunung SET isFavorite = :isFavorite WHERE name = :name")
    suspend fun updateFavoriteStatus(name: String, isFavorite: Boolean)

}