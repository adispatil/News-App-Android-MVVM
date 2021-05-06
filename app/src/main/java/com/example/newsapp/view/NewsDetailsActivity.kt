package com.example.newsapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityNewsDetailsBinding
import com.example.newsapp.utils.*
import com.example.newsapp.viewmodel.NewsListViewModel
import com.example.newsapp.viewmodel.NewsListViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


/**
 * Created by Aditya Patil on 06-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
class NewsDetailsActivity : AppCompatActivity(), KodeinAware,
    ConnectivityReceiver.ConnectivityReceiverListener {

    override val kodein by kodein()

    private val factory: NewsListViewModelFactory by instance<NewsListViewModelFactory>()
    private lateinit var mViewModel: NewsListViewModel
    private lateinit var mBinding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_details)
        mViewModel = ViewModelProvider(this, factory).get(NewsListViewModel::class.java)
        mBinding.viewModel = mViewModel

        setStatusBarColor()

        val articleJson: String = try {
            intent.extras?.getString(AppConstants.BUNDLE_NEWS_ARTICLE).toString()
        } catch (ex: Exception) {
            ""
        }
        mViewModel.getIntentData(articleJson)
        initListener()
    }

    private fun initListener() {
        mViewModel.event.observe(
            this,
            EventObserver {
                val content = it as Array<*>
                when (content[0] as String) {
                    EventConstants.BACK_CLICK_EVENT -> onBackClicked()
                    EventConstants.READ_MORE_EVENT -> onReadMoreClicked(content[1] as String)
                }
            }
        )
    }

    private fun onReadMoreClicked(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun onBackClicked() {
        this@NewsDetailsActivity.finish()
    }

    private fun setStatusBarColor() {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkBackground)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            mBinding.parentLayout.snackbar("Sorry! Not connected to internet")
        }
    }

    companion object {
        private val TAG = NewsDetailsActivity::class.java.simpleName
    }
}