package com.sample.domain.model

sealed class SearchDataState {
    data object Idle: SearchDataState()
    data object Loading: SearchDataState()
    data class Success(val searchData: SearchData): SearchDataState()
    data class Error(val throwable: Throwable): SearchDataState()
}