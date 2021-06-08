package dev.mizoguche.bookmarkcompose.domain

data class Category(
    val title: String,
    val url: String,
    val description: String,
    val articles: List<Article>,
)