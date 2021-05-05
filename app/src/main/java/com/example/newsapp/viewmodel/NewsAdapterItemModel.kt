package com.example.newsapp.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.newsapp.data.network.response.Article

class NewsAdapterItemModel(
    articleItem: Article
) : BaseObservable() {
    @Bindable
    var title: String = ""

    @Bindable
    var description: String = ""

    init {
        title = articleItem.title
        description = articleItem.description
    }
}