package com.example.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.NewsApplication
import com.example.newsapp.R
import com.example.newsapp.data.network.response.Article
import com.example.newsapp.databinding.ActivityNewsListBinding
import com.example.newsapp.utils.*
import com.example.newsapp.viewmodel.NewsListViewModel
import com.example.newsapp.viewmodel.NewsListViewModelFactory
import com.google.gson.Gson
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
class NewsListActivity : AppCompatActivity(), KodeinAware,
    ConnectivityReceiver.ConnectivityReceiverListener {

    override val kodein by kodein()

    private val factory: NewsListViewModelFactory by instance()
    private lateinit var mViewModel: NewsListViewModel
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var mBinding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_list)
        mViewModel = ViewModelProvider(this, factory).get(NewsListViewModel::class.java)
        mBinding.viewModel = mViewModel

        mBinding.progressBar.show()
        initRecyclerView()
        initListener()
        mViewModel.getNewsListData()
        setStatusBarColor()
    }

    private fun initListener() {
        mViewModel.event.observe(
            this,
            EventObserver {
                val content = it as Array<*>
                when (content[0] as String) {
                    EventConstants.REST_API_EXCEPTION,
                    EventConstants.NO_INTERNET_CONNECTION_EVENT -> showApiErrorMessage(content[1] as String)
                    EventConstants.NEWS_ITEM_CLICK_EVENT -> navigateToNewsDetailsActivity(content[1] as Article)
                    EventConstants.HIDE_PROGRESS_BAR -> mBinding.progressBar.hide()
                }
            }
        )
    }

    private fun navigateToNewsDetailsActivity(article: Article) {
        val json = Gson().toJson(article)

        startActivity(Intent(this@NewsListActivity, NewsDetailsActivity::class.java).putExtra(AppConstants.BUNDLE_NEWS_ARTICLE, json))
    }

    private fun showApiErrorMessage(message: String) {
        mBinding.parentLayout.snackbarError(message)
        mBinding.progressBar.hide()
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.recyclerNews.layoutManager = layoutManager
        mBinding.recyclerNews.isNestedScrollingEnabled = true
        mBinding.recyclerNews.setHasFixedSize(true)
    }

    private fun setStatusBarColor() {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkBackground)
    }

    override fun onResume() {
        super.onResume()
        NewsApplication().getInstance()?.setConnectivityListener(this@NewsListActivity)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            mBinding.parentLayout.snackbar("Sorry! Not connected to internet")
        }
    }
}