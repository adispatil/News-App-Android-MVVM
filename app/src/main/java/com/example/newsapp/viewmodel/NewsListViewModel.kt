package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.adapter.NewsListAdapter
import com.example.newsapp.data.model.NewsModel
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.utils.Event

class NewsListViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    var mModel = NewsModel()
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    var mNewsAdapter = MutableLiveData(
        NewsListAdapter(
            mModel.diagnosesList, this
        )
    )


}