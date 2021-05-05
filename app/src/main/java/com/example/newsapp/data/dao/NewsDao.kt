package com.example.newsapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.newsapp.data.entities.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM NewsEntity")
    fun getDashboardData() : LiveData<NewsEntity>
}