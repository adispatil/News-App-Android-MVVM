package com.example.newsapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.newsapp.R
import com.squareup.picasso.Picasso

object SetDetailNewsImage {
    @BindingAdapter("load_image")
    @JvmStatic
    fun loadNewsImage(view: ImageView?, url: String?) {
        if (!url.isNullOrEmpty()) {
            Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.progress_loader)
                .into(view)
        } else {
            Picasso.get()
                .load(AppConstants.DUMMY_IMAGE_NOT_FOUND)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.progress_loader)
                .into(view)
        }
    }
}