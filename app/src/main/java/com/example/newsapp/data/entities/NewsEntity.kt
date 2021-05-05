package com.example.newsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = 0,
    val title: String? = ""
)