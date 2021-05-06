package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.adapter.NewsListAdapter
import com.example.newsapp.data.model.NewsModel
import com.example.newsapp.data.network.response.Article
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.utils.*
import com.google.gson.Gson


/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
class NewsListViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    var mModel = NewsModel()
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    var mNewsAdapter = MutableLiveData(
        NewsListAdapter(
            mModel.newsList, this
        )
    )



    fun getNewsListData() {
        getLocalData()
        Coroutines.main {
            try {
                val newsListResponse = repository.getNewsList()
                newsListResponse?.articles?.let {
                    mModel.newsList.clear()
                    for (articleList in it) {
                        mModel.newsList.add(articleList)
                    }
                    mNewsAdapter.value?.updateList(mModel.newsList)
                    _event.value = Event(EventConstants.HIDE_PROGRESS_BAR)
                    repository.saveNewsListToLocalDb(mModel.newsList)
                }
            } catch (ex: RestApiExceptions) {
                _event.value = Event(EventConstants.REST_API_EXCEPTION, ex.message.toString())
            } catch (ex: NoInternetException) {
                _event.value = Event(EventConstants.NO_INTERNET_CONNECTION_EVENT, ex.message.toString() + if (mModel.newsList.isNullOrEmpty()){""} else {"\n You can still read the news from local data!"})
            } catch (ex: Exception) {
                _event.value = Event(EventConstants.REST_API_EXCEPTION, ex.message.toString())
            }
        }
    }

    private fun getLocalData() {
        Coroutines.main {
            val newsList = repository.getLocalNewsList()
            if (!newsList.isNullOrEmpty()) {
                mModel.newsList = newsList
                mNewsAdapter.value?.updateList(mModel.newsList)
                _event.value = Event(EventConstants.HIDE_PROGRESS_BAR)
            }
        }
    }

    fun onNewsItemClicked(newsItem: Article) {
        _event.value = Event(EventConstants.NEWS_ITEM_CLICK_EVENT, newsItem)
    }

    fun getIntentData(articleJson: String) {
        val article = Gson().fromJson(articleJson, Article::class.java)
        mModel.article.value = article
    }

    fun onReadMoreClick() {
        _event.value = Event(EventConstants.READ_MORE_EVENT, mModel.article.value?.url ?: "")
    }

    fun onBackClick() {
        _event.value = Event(EventConstants.BACK_CLICK_EVENT)
    }




}