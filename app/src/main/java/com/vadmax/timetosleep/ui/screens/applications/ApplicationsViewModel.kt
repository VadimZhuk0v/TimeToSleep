package com.vadmax.timetosleep.ui.screens.applications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vadmax.io.data.AppInfo
import com.vadmax.io.domain.usercases.GetSelectedApps
import com.vadmax.io.domain.usercases.SelectApp
import com.vadmax.io.domain.usercases.UnselectApp
import com.vadmax.timetosleep.domain.usercases.GetAppsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationsViewModel(
    private val getAppsList: GetAppsList,
    private val getSelectedApps: GetSelectedApps,
    private val selectApp: SelectApp,
    private val unselectApp: UnselectApp,
) : ViewModel() {

    private val appsList = MutableLiveData<List<AppInfo>>(listOf())
    private val _search = MutableLiveData("")

    private val _displayedApps = MediatorLiveData<List<AppInfo>>().apply {
        val merge = {
            val list = appsList.value ?: listOf()
            val searchRequest = _search.value ?: ""
            value = if (searchRequest.isEmpty()) {
                list
            } else {
                list.filter { it.name.contains(searchRequest, true) }
            }
        }
        addSource(_search) {
            merge()
        }
        addSource(appsList) {
            merge()
        }
    }
    val selectedApps = getSelectedApps().asLiveData()
    val displayedApps: LiveData<List<AppInfo>> = _displayedApps
    val search: LiveData<String> = _search

    init {
        viewModelScope.launch(Dispatchers.IO) {
            appsList.postValue(getAppsList())
        }
    }

    fun setSearch(text: String) {
        _search.value = text
    }

    fun selectAppInfo(appInfo: AppInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedApps.value?.contains(appInfo) == true) {
                unselectApp(appInfo)
            } else {
                selectApp(appInfo)
            }
        }
    }
}
