package com.feevlic.justgraphql.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [CountryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}