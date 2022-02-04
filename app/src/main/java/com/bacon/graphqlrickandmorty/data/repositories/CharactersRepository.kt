package com.bacon.graphqlrickandmorty.data.repositories

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.bacon.graphqlrickandmorty.CharacterQuery
import com.bacon.graphqlrickandmorty.CharactersListQuery
import com.bacon.graphqlrickandmorty.data.apiservices.CharactersApiService
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val service: CharactersApiService,
) {
    suspend fun queryCharactersList(): Response<CharactersListQuery.Data> {
        return service.getApolloClient().query(CharactersListQuery()).await()
    }
    suspend fun queryCharacter(id: String): Response<CharacterQuery.Data> {
        return service.getApolloClient().query(CharacterQuery(id)).await()
    }
}