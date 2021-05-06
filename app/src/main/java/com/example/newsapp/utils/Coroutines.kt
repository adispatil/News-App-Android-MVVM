package com.example.newsapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
object Coroutines {
    fun main(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

    fun io(work: suspend (()-> Unit)) =
        CoroutineScope((Dispatchers.IO)).launch {
            work()
        }
}