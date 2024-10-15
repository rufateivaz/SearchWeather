package com.sample.data.search.localdatasource

import android.content.SharedPreferences

/**
 * Shared preferences manager to store/get latest query.
 * */
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