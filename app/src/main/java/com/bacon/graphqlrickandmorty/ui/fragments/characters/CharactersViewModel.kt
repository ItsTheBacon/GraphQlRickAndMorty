package com.bacon.graphqlrickandmorty.ui.fragments.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.bacon.graphqlrickandmorty.CharactersListQuery
import com.bacon.graphqlrickandmorty.common.base.BaseViewModel
import com.bacon.graphqlrickandmorty.data.repositories.CharactersRepository
import com.bacon.graphqlrickandmorty.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository,
) : BaseViewModel() {
    private val _charactersList by lazy { MutableLiveData<UIState<Response<CharactersListQuery.Data>>>() }
    val charactersList: LiveData<UIState<Response<CharactersListQuery.Data>>>
        get() = _charactersList


    fun queryCharactersList() = viewModelScope.launch {
        _charactersList.postValue(UIState.Loading())
        try {
            val response = repository.queryCharactersList()
            _charactersList.postValue(UIState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _charactersList.postValue(UIState.Error("Error fetching characters"))
        }
    }
}