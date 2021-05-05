package com.example.newsapp.data.repositories

import com.example.newsapp.data.AppDatabase
import com.example.newsapp.data.network.RestApis
import com.example.newsapp.data.network.SafeApiRequest
import com.example.newsapp.data.network.response.NewsApiResponse

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
class NewsRepository(
    private val api: RestApis,
    private val db: AppDatabase
) : SafeApiRequest() {
    suspend fun getNewsList(): NewsApiResponse? {
        return apiRequest { api.getNewsList() }
    }
}