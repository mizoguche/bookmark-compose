package dev.mizoguche.bookmarkcompose.ui

import android.R
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import dev.mizoguche.bookmarkcompose.ui.theme.BookmarkComposeTheme

@Composable
fun TextPlaceholder(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    lineCount: Int = 1,
) {
    require(lineCount > 0)

    val infiniteTransition = rememberInfiniteTransition()
    val background by infiniteTransition.animateColor(
        initialValue = Color(0xFFCCCCCC),
        targetValue = Color(0xFF666666),
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse,
        )
    )
    val lineHeight = if (textStyle.lineHeight != TextUnit.Unspecified) {
        textStyle.lineHeight.value.dp
    } else {
        textStyle.fontSize.value.dp / 2
    }

    LazyColumn(
        modifier = modifier.wrapContentHeight(),
    ) {
        items(lineCount, key = { it }) {
            if (it != 0) {
                Spacer(modifier = Modifier.height(lineHeight))
            }

            Placeholder(
                modifier = modifier
                    .background(
                        color = background,
                        shape = MaterialTheme.shapes.medium,
                    )
                    .height(textStyle.fontSize.value.dp),
            )
        }
    }
}

@Composable
fun Placeholder(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
) {
    val infiniteTransition = rememberInfiniteTransition()
    val background by infiniteTransition.animateColor(
        initialValue = Color(0xFFCCCCCC),
        targetValue = Color(0xFF666666),
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse,
        )
    )

    Box(
        modifier = modifier
            .background(
                color = background,
                shape = shape,
            ),
    )
}

@Preview(showBackground = true)
@Composable
fun TextPlaceholderPreview() {
    BookmarkComposeTheme {
        TextPlaceholder(
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.body1,
            lineCount = 3,
        )
    }
}