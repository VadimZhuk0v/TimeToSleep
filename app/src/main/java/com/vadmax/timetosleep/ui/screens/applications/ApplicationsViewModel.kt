package com.vadmax.timetosleep.ui.screens.applications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadmax.core.data.AppInfo
import com.vadmax.timetosleep.domain.usercases.GetAppsList
import com.vadmax.timetosleep.domain.usercases.GetSelectedApps
import com.vadmax.timetosleep.domain.usercases.SelectApp
import com.vadmax.timetosleep.domain.usercases.UnselectApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ApplicationsViewModel(
    private val getAppsList: GetAppsList,
    private val getSelectedApps: GetSelectedApps,
    private val selectApp: SelectApp,
    private val unselectApp: UnselectApp,
) : ViewModel() {

    private val appsList = MutableStateFlow(listOf<AppInfo>())
    private val _search = MutableStateFlow("")

    private val _displayedApps = combine(appsList, _search) { appListValue, searchValue ->
        if (searchValue.isEmpty()) {
            appListValue
        } else {
            appListValue.filter { it.name.contains(searchValue, true) }
        }
    }
    val selectedApps = getSelectedApps()
    val displayedApps: Flow<List<AppInfo>> = _displayedApps
    val search: Flow<String> = _search

    init {
        viewModelScope.launch(Dispatchers.IO) {
            appsList.emit(getAppsList())
        }
    }

    fun setSearch(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _search.emit(text)
        }
    }

    fun selectAppInfo(appInfo: AppInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedApps.collectLatest {
                if (it.contains(appInfo)) {
                    unselectApp(appInfo)
                } else {
                    selectApp(appInfo)
                }
            }
        }
    }
}
