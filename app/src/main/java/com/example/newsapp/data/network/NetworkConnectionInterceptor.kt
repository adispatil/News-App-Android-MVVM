package com.example.newsapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.newsapp.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
class NetworkConnectionInterceptor(
    private val context: Context,
) : Interceptor {

    private val applicationContext: Context = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null

        if (!isInternetAvailable()) {
            throw NoInternetException("Make sure you have an active data connection")
        }

        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()

        try {
            response = chain.proceed(request)
        } catch (ex: Exception) {
            Log.e(TAG, "No internet exception : ${ex.message.toString()}")
        }

        if (response != null && response.code == 401) {
            Log.e("Interceptor", "Unauthorised request")
        } else if (response != null && response.code == 500) {
            Log.e("Interceptor", "Server error")
        } else {
            Log.e("Interceptor", "Something went wrong")
        }

        return response!!
    }

    /**
     * Check weather internet is available or not
     * @return Boolean
     */
    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    companion object {
        val TAG = NetworkConnectionInterceptor::class.java.simpleName
    }
}