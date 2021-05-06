package com.example.newsapp.view

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
import com.example.newsapp.databinding.ActivityNewsListBinding
import com.example.newsapp.utils.ConnectivityReceiver
import com.example.newsapp.utils.EventObserver
import com.example.newsapp.utils.snackbar
import com.example.newsapp.viewmodel.NewsListViewModel
import com.example.newsapp.viewmodel.NewsListViewModelFactory
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

    private val factory: NewsListViewModelFactory by instance<NewsListViewModelFactory>()
    private lateinit var mViewModel: NewsListViewModel
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var mBinding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_list)
        mViewModel = ViewModelProvider(this, factory).get(NewsListViewModel::class.java)
        mBinding.viewModel = mViewModel

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
//                    EventConstant.BACK_PRESS_EVENT -> onBackClicked()
//                    EventConstant.VISIT_HISTORY_CLICK_EVENT -> navigateToVisitHistory()
                }
            }
        )
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

    companion object {
        private val TAG = NewsListActivity::class.java.simpleName
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            mBinding.parentLayout.snackbar("Sorry! Not connected to internet")
        }
    }
}