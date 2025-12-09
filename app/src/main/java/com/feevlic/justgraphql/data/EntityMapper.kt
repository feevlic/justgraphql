package com.feevlic.justgraphql.data

import com.feevlic.justgraphql.data.db.CountryEntity
import com.feevlic.justgraphql.domain.DetailedCountry
import com.feevlic.justgraphql.domain.SimpleCountry

fun CountryEntity.toSimpleCountry(): SimpleCountry {
    return SimpleCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital
    )
}

fun CountryEntity.toDetailedCountry(): DetailedCountry {
    return DetailedCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital,
        currency = currency ?: "N/A",
        languages = if (languagesCsv.isBlank()) emptyList() else languagesCsv.split("|")
            .map { it.trim() },
        continent = continent
    )
}

fun DetailedCountry.toEntity(updateAtMs: Long): CountryEntity {
    return CountryEntity(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital,
        currency = currency,
        languagesCsv = languages.joinToString("|"),
        continent = continent,
        lastUpdatedEpochMs = updateAtMs
    )
}

fun SimpleCountry.toEntity(updatedAtMs: Long): CountryEntity {
    return CountryEntity(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital,
        currency = null,
        languagesCsv = "",
        continent = "",
        lastUpdatedEpochMs = updatedAtMs
    )
}