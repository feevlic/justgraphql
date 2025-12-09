package com.feevlic.justgraphql.data

import com.feevlic.justgraphql.data.db.CountryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncManager @Inject constructor(private val remote: ApolloCountyClient, val dao: CountryDao) {
    suspend fun syncAll(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val fetched = remote.getCountries()
            if (fetched.isNotEmpty()) {
                val now = System.currentTimeMillis()
                dao.insertAll(fetched.map { it.toEntity(now) })
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}