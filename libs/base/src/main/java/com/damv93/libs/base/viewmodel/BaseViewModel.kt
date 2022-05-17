package com.damv93.libs.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damv93.libs.base.viewstate.BaseViewState

/**
 * Base implementation of ViewModels that handle an immutable view state.
 */
abstract class BaseViewModel<VS : BaseViewState> : ViewModel() {

    abstract val initialState: VS

    private val _observableState: MutableLiveData<VS> = MutableLiveData()

    /**
     * State that the view will be observing/listening.
     * A LiveData is exposed to the View instead of a MutableLiveData.
     */
    val observableState: LiveData<VS> = _observableState

    var state: VS
        get() = _observableState.value ?: initialState
        /**
         * Sends a new immutable state to the view.
         * The current state is not modified, instead a new state is set.
         */
        set(value) = _observableState.setValue(value)

    var stateAsync: VS
        get() = state
        set(value) = _observableState.postValue(value)
}