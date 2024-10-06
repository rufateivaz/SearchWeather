package com.sample.data.localdatasource

import android.content.SharedPreferences

class SearchPreferencesManager(
    private val sharedPreferences: SharedPreferences
) {
    fun getQuery() = sharedPreferences.getString(PREF_KEY_SEARCH_QUERY, null)

    fun setQuery(query: String) {
        sharedPreferences.edit().putString(PREF_KEY_SEARCH_QUERY, query).apply()
    }

    companion object {
        private const val PREF_KEY_SEARCH_QUERY = "pref_key_search_query"
    }
}