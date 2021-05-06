package com.example.newsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.entities.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM NewsEntity")
    fun getLocalNewsList() : List<NewsEntity>

    @Query("DELETE FROM NewsEntity")
    fun deleteAllNewsItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllNewsList(newsList: List<NewsEntity>?)
}