package dev.mizoguche.bookmarkcompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mizoguche.bookmarkcompose.data.HatebuRssClient
import dev.mizoguche.bookmarkcompose.domain.Article
import dev.mizoguche.bookmarkcompose.logDebug
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ViewStatus {
    data class Ready(val isRefreshing: Boolean) : ViewStatus()
    object Loading : ViewStatus()
    object Error : ViewStatus()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: HatebuRssClient,
) : ViewModel() {
    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())
    val articles: StateFlow<List<Article>> = _articles

    private val _viewStatus: MutableStateFlow<ViewStatus> = MutableStateFlow(ViewStatus.Loading)
    val viewStatus: StateFlow<ViewStatus> = _viewStatus

    fun refresh() {
        viewModelScope.launch {
            _viewStatus.emit(
                if (articles.value.isEmpty()) {
                    ViewStatus.Loading
                } else {
                    ViewStatus.Ready(isRefreshing = true)
                }
            )
            runCatching { client.getHotentry() }
                .onSuccess {
                    delay(1000)
                    _articles.emit(it.articles)
                    _viewStatus.emit(ViewStatus.Ready(isRefreshing = false))
                }
                .onFailure {
                    logDebug("failure: throwable=$it")
                    _viewStatus.emit(ViewStatus.Error)
                }
        }
    }
}