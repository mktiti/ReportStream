package com.mktiti.reportstream.model

import com.mktiti.reportstream.model.Article
import com.mktiti.reportstream.model.Language
import com.mktiti.reportstream.model.LanguageFilter

interface ArticleService {

    fun availableLanguages(): List<Language>

    fun fetchArticles(langFilter: LanguageFilter): List<Article>

}
