package com.bacon.graphqlrickandmorty.ui.fragments.characters

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.bacon.graphqlrickandmorty.CharactersListQuery
import com.bacon.graphqlrickandmorty.common.base.BaseViewModel
import com.bacon.graphqlrickandmorty.data.repositories.CharactersRepository
import com.bacon.graphqlrickandmorty.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository,
) : BaseViewModel() {
    private val _charactersList =
        MutableStateFlow<UIState<Response<CharactersListQuery.Data>>>(UIState.Loading())
    val charactersList = _charactersList.asStateFlow()


    fun queryCharactersList() = viewModelScope.launch {
        _charactersList.value = UIState.Loading()
        try {
            val response = repository.queryCharactersList()
            _charactersList.value = UIState.Success(response)
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _charactersList.value = UIState.Error("Error fetching characters")
        }
    }
}