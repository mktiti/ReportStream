package com.mktiti.reportstream.ui

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mktiti.reportstream.MyApplication
import com.mktiti.reportstream.R
import com.mktiti.reportstream.db.ArticleDao
import com.mktiti.reportstream.model.ArticleService
import com.mktiti.reportstream.model.LanguagesResponse
import com.mktiti.reportstream.presenter.ArticleItem
import com.mktiti.reportstream.presenter.ArticlePresenter
import com.mktiti.reportstream.presenter.ServicePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var dao: ArticleDao

    private lateinit var articleService: ArticleService

    private lateinit var articlePresenter: ArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MyApplication).appComponent.inject(this)
        articleService = retrofit.create(ArticleService::class.java)
        articlePresenter = ServicePresenter(articleService = articleService)

        val articleAdapter = ArticleCardAdapter()

        articlePresenter.articles.observe(this, Observer { articles ->
            articleAdapter.set(articles)
        })

        val articles = findViewById<RecyclerView>(R.id.articles_recycler).apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context)
        }

        articlePresenter.loadArticles(emptyList())
        Log.i("MainActivity", dao.articles().value?.joinToString() ?: "")


        articleService.availableLanguages().enqueue(object : Callback<LanguagesResponse> {
            override fun onFailure(call: Call<LanguagesResponse>, t: Throwable) {
                Log.e("MainActivity", "Failed to fetch languages", t)
            }

            override fun onResponse(
                call: Call<LanguagesResponse>,
                response: Response<LanguagesResponse>
            ) {
                Log.i("MainActivity", "Available languages: " + response.body()?.languages)
            }

        })
    }

}
