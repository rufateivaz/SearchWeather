package com.sample.weather.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.domain.getsearchdatausecase.GetSearchDataUseCase
import com.sample.domain.getsearchqueryusecase.GetSearchQueryUseCase
import com.sample.domain.model.SearchDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
   private val getSearchDataUseCase: GetSearchDataUseCase,
   private val getSearchQueryUseCase: GetSearchQueryUseCase
): ViewModel() {
   var initialSearchResultPresented = false

   val searchDataState: State<SearchDataState> get() = _searchDataState
   private val _searchDataState: MutableState<SearchDataState> = mutableStateOf(SearchDataState.Idle)

   fun clearState() {
      _searchDataState.value = SearchDataState.Idle
   }

   fun getSearchData(query: String) {
      _searchDataState.value = SearchDataState.Loading
      viewModelScope.launch {
         initialSearchResultPresented = true
         _searchDataState.value = getSearchDataUseCase.invoke(query)
      }
   }

   fun getSearchDataWithOldQuery() {
      _searchDataState.value = SearchDataState.Loading
      viewModelScope.launch {
         val query = getSearchQueryUseCase.invoke()
         if (query.isNotEmpty()) {
            getSearchData(query)
         }
         else {
            initialSearchResultPresented = true
            _searchDataState.value = SearchDataState.Idle
         }
      }
   }
}