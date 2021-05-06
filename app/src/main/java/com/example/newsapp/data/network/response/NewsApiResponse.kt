package com.example.newsapp.data.network.response

data class NewsApiResponse(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int?
)