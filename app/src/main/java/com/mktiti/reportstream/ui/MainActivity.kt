package com.mktiti.reportstream.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mktiti.reportstream.ReportStream
import com.mktiti.reportstream.R
import com.mktiti.reportstream.db.ArticleDao
import com.mktiti.reportstream.model.ArticleEntity
import com.mktiti.reportstream.model.ArticleService
import com.mktiti.reportstream.model.Language
import com.mktiti.reportstream.presenter.ArticlePresenter
import com.mktiti.reportstream.presenter.ServicePresenter
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private val allLanguages = Language(name = "All", code = "All")
    }

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var dao: ArticleDao

    private lateinit var articleService: ArticleService
    private lateinit var articlePresenter: ArticlePresenter

    @Volatile
    private var selectedLanguage: Language? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as ReportStream).appComponent.inject(this)
        articleService = retrofit.create(ArticleService::class.java)
        articlePresenter = ServicePresenter(articleService = articleService)

        setSupportActionBar(findViewById(R.id.main_toolbar))

        val articleAdapter = ArticleCardAdapter {
            val intent = Intent(this, ArticleActivity::class.java).apply {
                putExtra("address", it.toString())
            }
            startActivity(intent)
        }


        val languageAdapter = object : ArrayAdapter<Language>(this, R.layout.support_simple_spinner_dropdown_item, mutableListOf()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return super.getView(position, convertView, parent).apply {
                    visibility = View.GONE
                }
            }
        }

        articlePresenter.articles.observe(this, Observer { articles ->
            articleAdapter.set(articles)
            AsyncTask.execute {
                dao.deleteAll()
                dao.insertAll(articles.map { ArticleEntity(it) })
            }
        })

        articlePresenter.languages.observe(this, Observer { languages ->
            languageAdapter.clear()
            languageAdapter.add(allLanguages)
            languageAdapter.addAll(languages)
        })

        findViewById<RecyclerView>(R.id.articles_recycler).apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context)
            isClickable = true
        }

        findViewById<Spinner>(R.id.language_selector)?.apply {
            adapter = languageAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    selectedLanguage = null
                    articlePresenter.loadArticles(selectedLanguage)
                }

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, p3: Long) {
                    (parent.getItemAtPosition(position) as? Language)?.let { language ->
                        selectedLanguage = if (language === allLanguages) null else language
                        articlePresenter.loadArticles(selectedLanguage)
                    }
                }

            }
        }

        articlePresenter.loadLanguages()
        articlePresenter.loadArticles(selectedLanguage)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_about -> {
            startActivity(Intent(this, AboutActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
