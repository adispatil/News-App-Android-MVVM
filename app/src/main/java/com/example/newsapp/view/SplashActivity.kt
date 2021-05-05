package com.example.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.newsapp.R
import java.util.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setStatusBarColor()
        setTimerForSplash()
    }

    private fun setTimerForSplash() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(applicationContext, NewsListActivity::class.java))
            }
        }, 2000)
    }

    private fun setStatusBarColor() {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkBackground)
    }
}