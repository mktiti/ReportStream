package com.mktiti.reportstream

import com.mktiti.reportstream.model.*
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MockArticleLoader(
    private val languages: List<Language>,
    private val articles: List<Article>
) : ArticleService {

    private fun <T> mockCall(result: T): Call<T> = object : Call<T> {
        private var isExecuted = false

        override fun enqueue(callback: Callback<T>) {
            isExecuted = true
            callback.onResponse(this, Response.success(result))
        }

        override fun isExecuted() = isExecuted

        override fun timeout(): Timeout {
            TODO("Not yet implemented")
        }

        override fun clone(): Call<T> = this

        override fun isCanceled() = false

        override fun cancel() {
            TODO("Not yet implemented")
        }

        override fun execute(): Response<T> {
            isExecuted = true
            return Response.success(result)
        }

        override fun request(): Request {
            TODO("Not yet implemented")
        }

    }

    override fun availableLanguages(key: String): Call<LanguagesResponse> = mockCall(
        LanguagesResponse(languages)
    )

    override fun fetchArticles(key: String, langFilter: String?): Call<ArticlesResponse> = mockCall(
        ArticlesResponse(
            if (langFilter == null) {
                articles
            } else {
                articles.filter { it.language == langFilter }
            }
        )
    )

}
