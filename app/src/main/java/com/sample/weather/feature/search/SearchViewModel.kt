package com.sample.weather.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.data.di.MainDispatcher
import com.sample.domain.search.getsearchdatausecase.GetSearchDataUseCase
import com.sample.domain.search.getsearchqueryusecase.GetSearchQueryUseCase
import com.sample.domain.search.model.SearchDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The viewmodel that handles search operation and updating ui accordingly.
 * */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchDataUseCase: GetSearchDataUseCase,
    private val getSearchQueryUseCase: GetSearchQueryUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {
    var initialSearchResultPresented = false

    private val _searchDataState = MutableStateFlow<SearchDataState>(SearchDataState.Idle)
    val searchDataState: StateFlow<SearchDataState> = _searchDataState.asStateFlow()

    /**
     * Initializes the [searchDataState] back to Idle.
     * */
    fun initSearchDataState() {
        _searchDataState.value = SearchDataState.Idle
    }

    /**
     * Takes query as a parameter and performs search.
     * Updates ui with the received result: success/error
     *
     * @param query
     * */
    fun getSearchData(query: String) {
        _searchDataState.value = SearchDataState.Loading
        viewModelScope.launch(mainDispatcher) {
            initialSearchResultPresented = true
            _searchDataState.value = getSearchDataUseCase.invoke(query)
        }
    }

    /**
     * Performs search operation with the old query,
     * stored in shared preferences.
     * */
    fun getSearchDataWithOldQuery() {
        _searchDataState.value = SearchDataState.Loading
        viewModelScope.launch(mainDispatcher) {
            val query = getSearchQueryUseCase.invoke()
            if (query.isNotEmpty()) {
                getSearchData(query)
            } else {
                initialSearchResultPresented = true
                _searchDataState.value = SearchDataState.Idle
            }
        }
    }
}