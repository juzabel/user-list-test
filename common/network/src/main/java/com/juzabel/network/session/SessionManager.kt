package com.juzabel.network.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SessionManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val EXPIRES_IN = longPreferencesKey("expires_in")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun saveAuthToken(token: SessionToken) {
        context.dataStore.edit { session ->
            session[ACCESS_TOKEN] = token.accessToken
            session[EXPIRES_IN] = token.expiresIn
            session[REFRESH_TOKEN] = token.refreshToken
        }
    }
    suspend fun fetchAuthToken(): SessionToken? {
        val token = context.dataStore.data.map { session -> session[ACCESS_TOKEN] }.first()
        val expiresIn = context.dataStore.data.map { session -> session[EXPIRES_IN] }.first() ?: 0L
        val refreshToken =
            context.dataStore.data.map { session -> session[REFRESH_TOKEN] }.first() ?: ""
        return if (token.isNullOrBlank()) null else SessionToken(token, expiresIn, refreshToken)
    }
}
