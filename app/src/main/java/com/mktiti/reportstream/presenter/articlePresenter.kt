package com.mktiti.reportstream.presenter

interface ArticlePresenter {

    fun loadLanguages(): List<LanguageOption>

    fun loadArticles(languages: List<LanguageOption>?): List<ArticleItem>

}
