package com.example.newsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: String? = "",
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = ""
) {
    constructor(
        author: String? = "",
        content: String? = "",
        description: String? = "",
        publishedAt: String? = "",
        source: String? = "",
        title: String? = "",
        url: String? = "",
        urlToImage: String? = ""
    ) : this(0, author, content, description, publishedAt, source, title, url, urlToImage)
}