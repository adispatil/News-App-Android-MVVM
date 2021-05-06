package com.example.newsapp.viewmodel

import android.text.format.DateUtils
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.example.newsapp.data.network.response.Article
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NewsAdapterItemModel(
    articleItem: Article
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
    var imageUrl: String =
        "https://www.thehindu.com/news/national/tamil-nadu/mjk6m3/article34487185.ece/ALTERNATES/LANDSCAPE_615/05MAYTH--Stalin"

    init {
        title = articleItem.title ?: ""
        description = articleItem.description ?: ""
        sourceName = articleItem.source?.name ?: ""
        imageUrl = articleItem.urlToImage ?: ""
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

    companion object {
        @BindingAdapter("android:loadImage")
        fun loadImage(image: ImageView, imageUrl: String) {
            Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .noPlaceholder()
                .into(image)
        }
    }
}