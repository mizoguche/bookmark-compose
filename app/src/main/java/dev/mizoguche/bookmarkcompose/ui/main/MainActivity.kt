package dev.mizoguche.bookmarkcompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.mizoguche.bookmarkcompose.ui.theme.BookmarkComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookmarkComposeTheme {
                MainScreen(viewModel = viewModel)
            }
        }

        viewModel.refresh()
    }
}