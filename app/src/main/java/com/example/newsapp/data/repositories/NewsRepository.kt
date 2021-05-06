package com.example.newsapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.AppDatabase
import com.example.newsapp.data.entities.NewsEntity
import com.example.newsapp.data.network.RestApis
import com.example.newsapp.data.network.SafeApiRequest
import com.example.newsapp.data.network.response.Article
import com.example.newsapp.data.network.response.NewsApiResponse
import com.example.newsapp.data.network.response.Source
import com.example.newsapp.utils.AppConstants
import com.example.newsapp.utils.Coroutines
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
class NewsRepository(
    private val api: RestApis,
    private val db: AppDatabase
) : SafeApiRequest() {

    /**
     * Get new list from server
     * @return NewsApiResponse?
     */
    suspend fun getNewsList(): NewsApiResponse? {
        return apiRequest { api.getNewsList("IN", AppConstants.NEWS_API_KEY) }
    }

    /**
     * save list to local database
     * @param list List<Article>
     */
    suspend fun saveNewsListToLocalDb(list: List<Article>) {
        if (list.isNotEmpty()) {
            Coroutines.io {
                db.getNewsDao().deleteAllNewsItems()

                val newList: MutableList<NewsEntity> = mutableListOf()
                for (article in list) {
                    newList.add(
                        NewsEntity(
                            article.author?:"",
                            article.content?:"",
                            article.description?:"",
                            article.publishedAt?:"",
                            Gson().toJson(article.source?:""),
                            article.title?:"",
                            article.url?:"",
                            article.urlToImage?:""
                        )
                    )
                }

                db.getNewsDao().saveAllNewsList(newList)
                getLocalNewsList()
            }
        }
    }

    suspend fun getLocalNewsList(): ArrayList<Article> {
        return withContext(Dispatchers.IO) {
            var tempList = ArrayList<Article>()
            val localList = db.getNewsDao().getLocalNewsList()
            for (article in localList) {
                tempList.add(
                    Article(
                        article.author,
                        article.content,
                        article.description,
                        article.publishedAt,
                        Gson().fromJson(article.source, Source::class.java),
                        article.title,
                        article.url,
                        article.urlToImage,
                    )
                )
            }
            Log.e("Data size", "Size : " + tempList.size)
            tempList
        }
    }
}