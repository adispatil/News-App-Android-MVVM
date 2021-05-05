package com.example.newsapp.data.network

import com.example.newsapp.utils.RestApiExceptions
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by Aditya Patil on 05-May-21.
 * aspatil9021@gmail.com
 * 9021-93-9021
 */
abstract class SafeApiRequest {
    suspend fun <T : Any?> apiRequest(call: suspend () -> Response<T>?): T? {
        val response = call.invoke()
        if (response != null && response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response?.errorBody()?.string()

            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (ex: Exception) {
                    ex.printStackTrace()
                } catch (ex: JSONException) {
                    ex.printStackTrace()
                }
                message.append("\n")
            }

            message.append("Error Code : ${response?.code()}")

            throw RestApiExceptions(message.toString())
        }
    }
}