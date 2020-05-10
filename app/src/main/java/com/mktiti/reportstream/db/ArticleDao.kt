package com.mktiti.reportstream.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mktiti.reportstream.model.ArticleEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun articles(): LiveData<List<ArticleEntity>>

    @Query("SELECT * from articles WHERE id = :id")
    fun articles(id: String): LiveData<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newList: List<ArticleEntity>)

    @Insert
    fun insert(newsEntity: ArticleEntity)

    @Update
    fun update(newsEntity: ArticleEntity)

    @Delete
    fun delete(newsEntity: ArticleEntity)

    @Query("DELETE FROM articles")
    fun deleteAll()
}