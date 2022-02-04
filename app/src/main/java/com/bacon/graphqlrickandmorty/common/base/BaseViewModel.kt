package com.bacon.graphqlrickandmorty.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacon.graphqlrickandmorty.common.resource.Resource
import com.bacon.graphqlrickandmorty.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    protected fun <T> MutableLiveData<UIState<T>>.subscribeTo(
        request: () -> Flow<Resource<T>>,
    ) {
        viewModelScope.launch {
            request().collect {
                when (it) {
                    is Resource.Loading -> {
                        this@subscribeTo.value = UIState.Loading()
                    }
                    is Resource.Error -> it.message?.let { error ->
                        this@subscribeTo.value = UIState.Error(error)
                    }
                    is Resource.Success -> it.data?.let { data ->
                        this@subscribeTo.value = UIState.Success(data)
                    }
                }
            }
        }
    }
}