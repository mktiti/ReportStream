package com.mktiti.reportstream.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mktiti.reportstream.presenter.ArticleItem
import java.lang.reflect.Type
import java.time.LocalDateTime

data class Language(
    val name: String,
    val code: String
) {
    override fun toString() = name
}

data class LanguagesResponse(
    val languages: List<Language>
)

object LanguagesDeserializer : JsonDeserializer<LanguagesResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LanguagesResponse {
        val languagesObj = json?.asJsonObject?.get("languages")?.asJsonObject ?: throw IllegalArgumentException("Bad json for languages")

        val list = languagesObj.entrySet().map { (key, value) ->
            Language(
                name = key,
                code = value.asString
            )
        }

        return LanguagesResponse(list)
    }

}

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey @ColumnInfo val id: String,
    @ColumnInfo val title: String?,
    @ColumnInfo val description: String?,
    @ColumnInfo val image: String?,
    @ColumnInfo val language: String?,
    @ColumnInfo val published: String?,
    @ColumnInfo val url: String?
) {

    constructor(article: ArticleItem) : this(
        id = article.id,
        title = article.title,
        description = article.description,
        language = "",
        image = article.image,
        published = article.published,
        url = article.uri.toString()
    )

}

data class Article(
    val id: String,
    val author: String,
    val title: String,
    val description: String,
    val published: LocalDateTime?,
    val url: String,
    @SerializedName("image")
    val imageUrl: String?
)

data class ArticlesResponse(
    @SerializedName("news")
    @Expose
    var news: List<Article>? = null
)
