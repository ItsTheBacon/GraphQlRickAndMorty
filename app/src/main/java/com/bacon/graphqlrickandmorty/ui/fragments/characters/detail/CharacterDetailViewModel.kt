package com.bacon.graphqlrickandmorty.ui.fragments.characters.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.bacon.graphqlrickandmorty.CharacterQuery
import com.bacon.graphqlrickandmorty.common.base.BaseViewModel
import com.bacon.graphqlrickandmorty.data.repositories.CharactersRepository
import com.bacon.graphqlrickandmorty.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharactersRepository,
) : BaseViewModel() {

    private val _characterState =
        MutableStateFlow<UIState<Response<CharacterQuery.Data>>>(UIState.Loading())
    val characterState = _characterState.asStateFlow()

    fun queryCharacter(id: String) = viewModelScope.launch {
        _characterState.value = UIState.Loading()
        try {
            val response = repository.queryCharacter(id)
            _characterState.value = UIState.Success(response)
        } catch (ae: ApolloException) {
            Log.d("ApolloException", "Failure", ae)
            _characterState.value = UIState.Error("Error fetching characters")
        }
    }

}