package com.feevlic.justgraphql.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey val code: String,
    val name: String,
    val emoji: String,
    val capital: String,
    val currency: String?,
    val languagesCsv: String,
    val continent: String,
    val lastUpdatedEpochMs: Long
)
