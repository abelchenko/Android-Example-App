package com.example.exampleapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampleapplication.service.DataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val dataManager: DataManager) : ViewModel() {

    private val _navigation = Channel<Navigation>(Channel.CONFLATED)
    val navigation: Flow<Navigation> = _navigation.receiveAsFlow()

    private val _listStateFlow = MutableStateFlow(ListState(emptyList(), "Title"))
    val listStateFlow: StateFlow<ListState> = _listStateFlow.asStateFlow()

    private val _detailStateFlow =
        MutableStateFlow(DetailState(UIEntity(-1, "Name"), false))
    val detailStateFlow: StateFlow<DetailState> = _detailStateFlow.asStateFlow()

    public fun getList() {
        viewModelScope.launch {
            val list = dataManager.getList()
            //UI related logic should happen here
            val uiList = list.map { UIEntity(it.id, it.name) }
            _listStateFlow.value = ListState(uiList, "New Title")
        }
    }

    public fun getEntity(id: Long) {
        viewModelScope.launch {
            val entity = dataManager.getEntity(id)
            //UI related logic should happen here
            _detailStateFlow.value = DetailState(UIEntity(entity.id, entity.name), false)
        }
    }

    sealed class Navigation {
        object Detail : Navigation()
        object List : Navigation()
        object Back: Navigation()
    }

    data class ListState(val entities: List<UIEntity>, val listTitle: String)

    data class DetailState(val entity: UIEntity, val authorised: Boolean)
}

data class UIEntity(val id: Long, val formattedName: String)