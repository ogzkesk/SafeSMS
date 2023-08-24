package com.ogzkesk.safesms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.core.ext.flowOnIO
import com.ogzkesk.core.ext.launchIO
import com.ogzkesk.core.util.Constants.EMPTY
import com.ogzkesk.domain.use_case.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {


    private val phoneNumber: StateFlow<String> = preferences.readPhoneNumber
        .flowOnIO()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = EMPTY
        )


    fun setPhoneNumber(number: String){
        val phoneNumber = phoneNumber.value
        if(phoneNumber.isEmpty()){
            Timber.d("phoneNumber.isEmpty()")
            Timber.d("fetchedNumber :: $number")

            viewModelScope.launchIO {
                preferences.writePhoneNumber(number)
            }
        }
    }
}