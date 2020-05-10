package com.mktiti.reportstream.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mktiti.reportstream.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI
import java.time.format.DateTimeFormatter

interface ArticlePresenter {

    val articles: LiveData<List<ArticleItem>>

    val languages: LiveData<List<Language>>

    fun loadLanguages()

    fun loadArticles(language: Language?)

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

    private val mutLanguages = MutableLiveData<List<Language>>()
    override val languages: LiveData<List<Language>>
        get() = mutLanguages

    private fun Article.present(): ArticleItem = ArticleItem(
        title = title,
        author = author,
        description = description,
        published = published?.let { dateFormat.format(it) },
        image = imageUrl, //imageUrl?.let { dataLoader.loadBinary(URI.create(it)) },
        uri = URI.create(url)
    )

    override fun loadLanguages() {
        articleService.availableLanguages().enqueue(object : Callback<LanguagesResponse> {
            override fun onFailure(call: Call<LanguagesResponse>, t: Throwable) {
                Log.e("Article Service Presenter", "Failed to fetch languages", t)
                mutLanguages.value = emptyList()
            }

            override fun onResponse(
                call: Call<LanguagesResponse>,
                response: Response<LanguagesResponse>
            ) {
                Log.i("Article Service Presenter", "Fetched languages")
                mutLanguages.value = response.body()?.languages ?: emptyList()
            }

        })
    }

    override fun loadArticles(language: Language?) {
        articleService.fetchArticles(langFilter = language?.code).enqueue(object : Callback<ArticlesResponse> {
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
