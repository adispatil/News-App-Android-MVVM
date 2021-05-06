package com.example.newsapp.viewmodel

import android.text.format.DateUtils
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.example.newsapp.data.network.response.Article
import com.example.newsapp.utils.AppConstants
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NewsAdapterItemModel(
    val mViewModel: NewsListViewModel,
    val articleItem: Article
) : BaseObservable() {
    @Bindable
    var title: String = ""

    @Bindable
    var description: String = ""

    @Bindable
    var sourceName: String = ""

    @Bindable
    var newsTimeAgo: String = ""

    @Bindable
    var imageUrl: String = ""

    init {
        title = articleItem.title ?: ""
        description = articleItem.description ?: articleItem.content ?: ""
        sourceName = articleItem.source?.name ?: ""
        imageUrl = articleItem.urlToImage ?: AppConstants.DUMMY_IMAGE_NOT_FOUND
        if (!articleItem.publishedAt.isNullOrEmpty()) {
            newsTimeAgo = getTimeAgo(articleItem.publishedAt)
        }
    }

    private fun getTimeAgo(publishedAt: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return try {
            val time: Long = sdf.parse(publishedAt).time
            val now = System.currentTimeMillis()
            val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
            ago.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun onNewsItemClicked() {
        mViewModel.onNewsItemClicked(articleItem)
    }
}