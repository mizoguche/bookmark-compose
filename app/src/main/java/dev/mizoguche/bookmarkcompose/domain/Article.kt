package dev.mizoguche.bookmarkcompose.domain

import java.time.LocalDateTime

data class Article(
    val title: String,
    val url: String,
    val description: String,
    val date: LocalDateTime,
    val bookmarkCount: Int,
    val imageUrl: String,
)