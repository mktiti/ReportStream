package com.mktiti.reportstream.model

import com.mktiti.reportstream.network.NetModule
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ArticleService {

    @GET("available/languages")
    @Headers("Content-type: application/json")
    fun availableLanguages(@Query("apiKey") key: String = NetModule.API_KEY): Call<LanguagesResponse>

    @GET("latest-news")
    @Headers("Content-type: application/json")
    fun fetchArticles(@Query("apiKey") key: String = NetModule.API_KEY, @Query("language") langFilter: Language?): Call<ArticlesResponse>

}
