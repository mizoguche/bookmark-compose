package dev.mizoguche.bookmarkcompose.data

import dev.mizoguche.bookmarkcompose.domain.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await
import javax.inject.Inject

class HatebuRssClient @Inject constructor() {
    private val okHttpClient: OkHttpClient = OkHttpClient()

    private suspend fun get(url: String): Category {
        val rssString = withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()
            okHttpClient.newCall(request).await().body?.string()
        } ?: throw IllegalStateException("no response")
        println(rssString)
        return HatebuRssParser.parse(rssString)
    }

    suspend fun getHotentry(): Category {
        return get("https://b.hatena.ne.jp/hotentry/all.rss")
    }
}