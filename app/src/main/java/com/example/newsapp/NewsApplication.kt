package com.example.newsapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.example.newsapp.data.AppDatabase
import com.example.newsapp.data.network.NetworkConnectionInterceptor
import com.example.newsapp.data.network.RestApis
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.utils.ConnectivityReceiver
import com.example.newsapp.viewmodel.NewsListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
class NewsApplication : Application(), KodeinAware {
    companion object {
        var mInstance: NewsApplication? = null
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@NewsApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { RestApis(instance()) }
        bind() from singleton { AppDatabase(instance()) }

        //repository declaration
        bind() from singleton { NewsRepository(instance(), instance()) }

        //view model factory declaration
        bind() from provider { NewsListViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityDestroyed(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityStopped(activity: Activity) {}

            @SuppressLint("SourceLockedOrientationActivity")
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            override fun onActivityResumed(activity: Activity) {}
        })
        mInstance = this@NewsApplication

    }

    @Synchronized
    fun getInstance(): NewsApplication? {
        return mInstance
    }

    fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityReceiverListener) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }
}