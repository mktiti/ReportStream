package com.mktiti.reportstream.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mktiti.reportstream.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface ArticlePresenter {

    val articles: LiveData<List<ArticleItem>>

    fun loadLanguages(): List<LanguageOption>

    fun loadArticles(languages: List<LanguageOption>?)

}

class ServicePresenter(
    private val dataLoader: DataLoader = DefaultLoader,
    private val articleService: ArticleService
) : ArticlePresenter {

    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("HH:mm")
    }

    private val mutArticles = MutableLiveData<List<ArticleItem>>()
    override val articles: LiveData<List<ArticleItem>>
        get() = mutArticles

    private fun Article.present(): ArticleItem = ArticleItem(
        title = title,
        author = author,
        description = description,
        published = published?.let { dateFormat.format(it) },
        image = imageUrl?.let { dataLoader.loadBinary(URI.create(it)) },
        uri = URI.create(url)
    )

    override fun loadLanguages(): List<LanguageOption> {
        TODO("Not yet implemented")
    }

    override fun loadArticles(languages: List<LanguageOption>?) {
        articleService.fetchArticles(langFilter = null).enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("Article Service Presenter", "Failed to fetch articles", t)
                mutArticles.value = emptyList()
            }

            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                Log.i("Article Service Presenter", "Fetched articles")
                mutArticles.value = response.body()?.news?.map { it.present() } ?: emptyList()
            }

        })
    }

}
