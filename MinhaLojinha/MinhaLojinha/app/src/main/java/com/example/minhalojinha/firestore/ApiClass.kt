package com.example.minhalojinha.firestore

import android.util.Log
import androidx.fragment.app.Fragment
import okhttp3.*
import java.io.IOException
import com.example.minhalojinha.ui.fragments.DashboardFragment
import org.json.JSONArray
import org.json.JSONTokener

class ApiClass {

    private val client = OkHttpClient()

    private var quote = "vino veritas"

    private var quotesArray = arrayListOf(quote)

    fun getQuoteZenList (fragment: Fragment){
        val request = Request.Builder()
            .url("https://zenquotes.io/api/today")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                when (fragment) {
//                    fragment.hideProgressDialog()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body()?.string()
                println(responseBody)
                when (fragment) {
                    is DashboardFragment -> {
                        val jsonArray = JSONTokener(responseBody).nextValue() as JSONArray
                        for (i in 0 until jsonArray.length()) {
                            // quote
                            val quoted = jsonArray.getJSONObject(i).getString("q")
                            Log.i("Quote: ", quoted)
                            quote = quoted
                        }
                        quotesArray = arrayListOf(quote)
                        fragment.successResponseQuote(quotesArray)
                        fragment.hideProgressDialog()
                    }
                }
            }
        })
    }

}