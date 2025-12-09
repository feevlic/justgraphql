package com.feevlic.justgraphql.data

import com.feevlic.justgraphql.data.db.CountryDao
import com.feevlic.justgraphql.domain.CountryClient
import com.feevlic.justgraphql.domain.DetailedCountry
import com.feevlic.justgraphql.domain.SimpleCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomCountryClient @Inject constructor(
    private val remote: ApolloCountyClient,
    private val dao: CountryDao
) :
    CountryClient {

    companion object {
        private const val TTL_MS = 24 * 60 * 1000L
    }

    override suspend fun getCountries(): List<SimpleCountry> = withContext(Dispatchers.IO) {
        val local = dao.getAll()
        val now = System.currentTimeMillis()
        val isLocalState = local.any { now - it.lastUpdatedEpochMs > TTL_MS } || local.isEmpty()

        if (!isLocalState) {
            return@withContext local.map { it.toSimpleCountry() }
        }

        val fetched = try {
            remote.getCountries()
        } catch (t: Throwable) {
            emptyList()
        }

        if (fetched.isNotEmpty()) {
            val entities = fetched.map { it.toEntity(System.currentTimeMillis()) }
            dao.insertAll(entities)
            return@withContext fetched
        }

        local.map { it.toSimpleCountry() }
    }

    override suspend fun getCountry(code: String): DetailedCountry? = withContext(Dispatchers.IO) {
        val local = dao.getByCode(code)
        if (local != null) {
            return@withContext local.toDetailedCountry()
        }

        val fetched = try {
            remote.getCountry(code)
        } catch (t: Throwable) {
            null
        }

        if (fetched != null) {
            dao.insert(fetched.toEntity(System.currentTimeMillis()))
        }
        fetched
    }
}