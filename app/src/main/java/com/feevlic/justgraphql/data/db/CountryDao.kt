package com.feevlic.justgraphql.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {

    @Query("SELECT * FROM countries ORDER BY name ASC")
    suspend fun getAll(): List<CountryEntity>

    @Query("SELECT * FROM countries WHERE code = :code LIMIT 1")
    suspend fun getByCode(code: String): CountryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(country: List<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: CountryEntity)

    @Query("DELETE FROM countries")
    suspend fun clearAll()
}