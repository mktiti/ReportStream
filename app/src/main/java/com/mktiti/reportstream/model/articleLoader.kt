package com.mktiti.reportstream.model

interface ArticleService {

    fun availableLanguages(): List<Language>

    fun fetchArticles(langFilter: LanguageFilter): List<Article>

}
