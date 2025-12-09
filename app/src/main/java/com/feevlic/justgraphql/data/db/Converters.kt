package com.feevlic.justgraphql.data.db

import androidx.room.TypeConverter

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromLanguagesCsv(csv: String?): List<String> {
        if (csv.isNullOrEmpty()) return emptyList()
        return csv.split('|').map { it.trim() }
    }

    @TypeConverter
    @JvmStatic
    fun toLanguagesCsv(list: List<String>?): String {
        return list?.joinToString(separator = "|") ?: ""
    }
}