package com.example.newsapp.viewmodel

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.adapter.NewsListAdapter
import com.example.newsapp.data.model.NewsModel
import com.example.newsapp.data.network.response.Article
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.utils.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso


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
        Coroutines.main {
            try {
                val newsListResponse = repository.getNewsList()
                newsListResponse?.articles?.let {
                    for (articleList in it) {
                        mModel.newsList.add(articleList)
                    }
                    mNewsAdapter.value?.updateList(mModel.newsList)
                }
            } catch (ex: RestApiExceptions) {
                _event.value = Event(EventConstants.REST_API_EXCEPTION, ex.message.toString())
            } catch (ex: Exception) {
                _event.value = Event(EventConstants.REST_API_EXCEPTION, ex.message.toString())
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