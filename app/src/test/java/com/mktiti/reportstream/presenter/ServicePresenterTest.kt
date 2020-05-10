package com.mktiti.reportstream.presenter

import com.mktiti.reportstream.MockArticleLoader
import com.mktiti.reportstream.model.Article
import com.mktiti.reportstream.model.Language
import org.junit.Test
import org.junit.Assert.*
import java.net.URI
import java.time.LocalDateTime
import java.time.Month

class ServicePresenterTest {

    companion object {
        val testLanguages = listOf(
            Language(name = "English", code = "en"),
            Language(name = "German", code = "de"),
            Language(name = "Hungarian", code = "hu"),
            Language(name = "French", code = "fr")
        )

        val testArticles = listOf(
            Article(
                id = "0",
                url = "www.asd.com",
                published = LocalDateTime.of(2020, Month.JANUARY, 1, 10, 20),
                language = "en",
                title = "Test english article #1",
                description = "Test english article #1 short description",
                author = "Test author",
                imageUrl = null
            ),
            Article(
                id = "1",
                url = "www.asd.com/2",
                published = LocalDateTime.of(2020, Month.JANUARY, 2, 10, 20),
                language = "en",
                title = "Test english article #2",
                description = "Test english article #2 short description",
                author = "Test author",
                imageUrl = null
            ),
            Article(
                id = "2",
                url = "www.asd.de",
                published = LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 20),
                language = "de",
                title = "Test german article #1",
                description = "Test german article #1 short description",
                author = "Test author german",
                imageUrl = null
            )
        )
    }

    private val servicePresenter = ServicePresenter(MockArticleLoader(
        languages = testLanguages,
        articles = testArticles
    ))

    @Test
    fun `test single article conversion`() {
        servicePresenter.loadArticles(null)
        servicePresenter.articles.value!!.find { it.id == "0" }!!.apply {
            assertEquals("Test english article #1", title)
            assertEquals(URI.create("www.asd.com"), uri)
            assertEquals("Test author", author)
            assertEquals("2:10", published)
            assertEquals("Test english article #1 short description", description)
        }
    }

    @Test
    fun `test no filter fetch`() {
        servicePresenter.loadArticles(null)
        assertEquals(listOf("0", "1", "2"), servicePresenter.articles.value!!.map { it.id })
    }

    @Test
    fun `test language filter`() {
        servicePresenter.loadArticles(Language("German", "de"))
        assertEquals(listOf("2"), servicePresenter.articles.value!!.map { it.id })
    }

    @Test
    fun `test language conversion`() {
        servicePresenter.loadLanguages()
        servicePresenter.languages.value!!.find { it.name == "Hungarian" }!!.apply {
            assertEquals("hu", code)
        }
    }

}
