package com.damv93.libs.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damv93.libs.base.viewstate.BaseViewState

abstract class BaseViewModel<VS : BaseViewState> : ViewModel() {

    abstract val initialState: VS

    private val _observableState: MutableLiveData<VS> = MutableLiveData()

    // Expose a LiveData to the View instead of a MutableLiveData
    val observableState: LiveData<VS> = _observableState

    var state: VS
        get() = _observableState.value ?: initialState
        set(value) = _observableState.setValue(value)

    var stateAsync: VS
        get() = state
        set(value) = _observableState.postValue(value)
}