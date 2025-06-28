package com.example.shared.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewEvent
interface ViewState
interface ViewSideEffect

const val SIDE_EFFECTS_KEY = "side_effects_key"

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect> :
    ViewModel() {

    abstract fun setInitialState(): UiState
    abstract fun handleEvents(event: Event)

    private val initialState: UiState by lazy { setInitialState() }

    private val _viewState = MutableStateFlow(initialState)

    @NativeCoroutinesState
    val viewState = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _sideEffect: Channel<Effect> = Channel()

    @NativeCoroutines
    val effect = _sideEffect.receiveAsFlow()

//    @NativeCoroutinesState
//    val effectIos = _sideEffect.receiveAsFlow().stateIn(
//        viewModelScope,
//        SharingStarted.Eagerly,
//        null
//    )

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = _viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        viewModelScope.launch {
            _sideEffect.send(builder())
        }
    }

}