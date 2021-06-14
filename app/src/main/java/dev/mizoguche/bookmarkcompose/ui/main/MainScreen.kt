package dev.mizoguche.bookmarkcompose.ui.main

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import dev.mizoguche.bookmarkcompose.R
import dev.mizoguche.bookmarkcompose.domain.Article
import dev.mizoguche.bookmarkcompose.logDebug
import dev.mizoguche.bookmarkcompose.ui.Placeholder
import dev.mizoguche.bookmarkcompose.ui.TextPlaceholder
import dev.mizoguche.bookmarkcompose.ui.theme.BookmarkComposeTheme
import java.time.LocalDateTime

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val articles = viewModel.articles.collectAsState()
    val viewStatus = viewModel.viewStatus.collectAsState()

    Surface(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Crossfade(targetState = viewStatus.value) {
                when (it) {
                    is ViewStatus.Ready -> {
                        Articles(
                            articles = articles.value,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                    is ViewStatus.Processing -> ArticlesPlaceholder()
                    is ViewStatus.Error -> Text(text = "error")
                }
            }
        }
    }
}

@Composable
fun Articles(
    modifier: Modifier = Modifier,
    articles: List<Article>,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        items(
            items = articles,
            key = { it.url }
        ) {
            ArticleItem(article = it)

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
fun ArticlesPlaceholder(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        items(
            count = 10,
            key = { it }
        ) {
            ArticleItemPlaceholder()

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
) {
    val context = LocalContext.current
    BoxWithConstraints(modifier = modifier) {
        Card(
            modifier = modifier
                .height(maxWidth)
                .width(maxWidth),
            elevation = 8.dp,
        ) {
            Column(modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.url)
                context.startActivity(intent)
            }) {
                BoxWithConstraints {
                    Image(
                        painter = rememberCoilPainter(
                            request = article.imageUrl,
                            previewPlaceholder = R.drawable.ic_launcher_foreground,
                        ),
                        contentDescription = article.title,
                        modifier = Modifier
                            .height(maxWidth * 9f / 16f)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = article.title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier,
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = article.description,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f),
                    )
                }
            }

        }
    }
}

@Composable
fun ArticleItemPlaceholder(
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        Card(
            modifier = modifier
                .height(maxWidth)
                .width(maxWidth),
            elevation = 8.dp,
        ) {
            Column {
                BoxWithConstraints {
                    Placeholder(
                        modifier = Modifier
                            .height(maxWidth * 9f / 16f)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp),
                        )
                    )

                    Spacer(modifier = Modifier.size(4.dp))
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    TextPlaceholder(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.h6,
                        lineCount = 2,
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    TextPlaceholder(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.body1,
                        lineCount = 3,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    BookmarkComposeTheme {
        ArticleItem(
            article =
            Article(
                title = "title",
                description = "description",
                date = LocalDateTime.now(),
                imageUrl = "https://dummyimage.com/600x400/000/fff",
                bookmarkCount = 0,
                url = "https://example.com/1",
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPlaceholderPreview() {
    BookmarkComposeTheme {
        ArticleItemPlaceholder()
    }
}
