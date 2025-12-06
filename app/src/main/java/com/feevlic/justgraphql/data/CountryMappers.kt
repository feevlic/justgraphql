package com.feevlic.justgraphql.data

import com.feevlic.CountriesQuery
import com.feevlic.CountryQuery
import com.feevlic.justgraphql.domain.DetailedCountry
import com.feevlic.justgraphql.domain.SimpleCountry

fun CountryQuery.Country.toDetailCountry(): DetailedCountry {
    return DetailedCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "N/A",
        currency = currency ?: "N/A",
        languages = languages.map { it.name },
        continent = continent.name,
    )
}

fun CountriesQuery.Country.toSimpleCountry(): SimpleCountry {
    return SimpleCountry(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "N/A",
    )
}