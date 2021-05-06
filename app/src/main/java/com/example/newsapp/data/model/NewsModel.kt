package com.example.newsapp.data.model

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.network.response.Article
import com.example.newsapp.utils.AppConstants

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
class NewsModel {
    var newsList = ArrayList<Article>()
    var article = MutableLiveData<Article>()
    var imageNotFount = AppConstants.DUMMY_IMAGE_NOT_FOUND
}