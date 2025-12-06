package com.feevlic.justgraphql.data


import com.apollographql.apollo3.ApolloClient
import com.feevlic.CountriesQuery
import com.feevlic.CountryQuery
import com.feevlic.justgraphql.domain.CountryClient
import com.feevlic.justgraphql.domain.DetailedCountry
import com.feevlic.justgraphql.domain.SimpleCountry

class ApolloCountyClient(private val apolloClient: ApolloClient) : CountryClient {
    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailCountry()
    }
}