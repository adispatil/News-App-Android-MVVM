package com.example.newsapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.network.response.Article

@Dao
interface NewsDao {
    @Query("SELECT * FROM NewsEntity")
    fun getDashboardData() : LiveData<NewsEntity>

    @Query("DELETE FROM NewsEntity")
    fun deleteAllNewsItems()

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun saveAllNewsList(newsList: List<Article>?)
}