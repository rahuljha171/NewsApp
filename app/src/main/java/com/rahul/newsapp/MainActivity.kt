package com.rahul.newsapp

import MySingleton
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemclicked {
    private lateinit var nAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager=LinearLayoutManager(this)
        fetchData()
        nAdapter=NewsListAdapter(this)

        recyclerView.adapter=nAdapter

    }

    private fun fetchData() {
        val url="http://newsapi.org/v2/top-headlines?country=in&apiKey=a13cd2a274a84257ad4b8ce468e0180a"
        val jsonObjectRequest= object:  JsonObjectRequest(
        Request.Method.GET, url,null,
        Response.Listener {
val newsjsonArrayList= it.getJSONArray("articles")
            val newsArray=ArrayList<News>()
            for (i in 0 until newsjsonArrayList.length())
            {
                val newsJsonObject=newsjsonArrayList.getJSONObject(i)
                val news=News(
                    newsJsonObject.getString("title"),
                    newsJsonObject.getString("author"),
                    newsJsonObject.getString("url"),
                    newsJsonObject.getString("urlToImage")
                )
                newsArray.add(news)
            }
            nAdapter.updateNews(newsArray)
        },

        Response.ErrorListener {
            Toast.makeText(this, "something went worng ", Toast.LENGTH_SHORT).show()
        })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    override fun onItemclicked(item: News) {
   val builder= CustomTabsIntent.Builder()
       val  CustomTabsIntent = builder.build()
        CustomTabsIntent.launchUrl(this, Uri.parse(item.url))

    }


}