package com.mktiti.reportstream.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mktiti.reportstream.R
import com.mktiti.reportstream.presenter.ArticleItem

class ArticleCardAdapter : RecyclerView.Adapter<ArticleCardAdapter.ArticleHolder>() {

    class ArticleHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.icon_view)
        val title: TextView = view.findViewById(R.id.title_view)
        val author: TextView = view.findViewById(R.id.author_view)
        val date: TextView = view.findViewById(R.id.date_view)
    }

    private val articles = mutableListOf<ArticleItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(LayoutInflater.from(parent.context).inflate(R.layout.article_card, parent, false))
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        with(articles[position]) {
            holder.icon.setImageDrawable(image)
            holder.title.text = title
            holder.author.text = author
            holder.date.text = published ?: ""
        }
    }

    fun set(articles: Iterable<ArticleItem>) {
        this.articles.clear()
        this.articles += articles
        notifyDataSetChanged()
    }

}