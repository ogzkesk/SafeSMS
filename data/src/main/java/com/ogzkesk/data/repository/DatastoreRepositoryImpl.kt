package com.ogzkesk.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ogzkesk.core.ext.flowOnIO
import com.ogzkesk.data.util.Constants.KEY_PHONE_NUMBER
import com.ogzkesk.data.util.dataStore
import com.ogzkesk.domain.repository.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DataStoreRepository {

    private object PreferenceKeys {
        val keyPhoneNumber = stringPreferencesKey(KEY_PHONE_NUMBER)
    }

    private val dataStore = context.dataStore

    override suspend fun writePhoneNumber(number: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.keyPhoneNumber] = number
        }
    }

    override val readPhoneNumber: Flow<String>
        get() = dataStore.data
            .flowOnIO()
            .map { prefs -> prefs[PreferenceKeys.keyPhoneNumber] ?: "" }
            .catch { emptyPreferences() }

}