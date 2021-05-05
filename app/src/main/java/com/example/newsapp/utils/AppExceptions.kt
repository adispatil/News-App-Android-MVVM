package com.example.newsapp.utils

import java.io.IOException


/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */

class RestApiExceptions(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)