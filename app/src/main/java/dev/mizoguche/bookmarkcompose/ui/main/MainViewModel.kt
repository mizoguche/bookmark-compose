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
    object Ready : ViewStatus()
    object Processing : ViewStatus()
    object Error : ViewStatus()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: HatebuRssClient,
) : ViewModel() {
    private val _articles: MutableStateFlow<List<Article>> = MutableStateFlow(listOf())
    val articles: StateFlow<List<Article>> = _articles

    private val _viewStatus: MutableStateFlow<ViewStatus> = MutableStateFlow(ViewStatus.Processing)
    val viewStatus: StateFlow<ViewStatus> = _viewStatus

    fun refresh() {
        viewModelScope.launch {
            _viewStatus.emit(ViewStatus.Processing)
            runCatching { client.getHotentry() }
                .onSuccess {
                    logDebug("success: channel=$it, ${it.title}")
                    delay(1000)
                    _articles.emit(it.articles)
                    _viewStatus.emit(ViewStatus.Ready)
                }
                .onFailure {
                    logDebug("failure: throwable=$it")
                    _viewStatus.emit(ViewStatus.Error)
                }
        }
    }
}