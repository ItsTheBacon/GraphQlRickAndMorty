package com.bacon.graphqlrickandmorty.data.apiservices

import android.os.Looper
import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

class CharactersApiService {

    fun getApolloClient(): ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }

        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }

}