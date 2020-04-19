package com.mktiti.reportstream.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mktiti.reportstream.model.ArticleEntity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun dao(): ArticleDao =
        Room.databaseBuilder(context, ArticleDb::class.java, "articles")
            .fallbackToDestructiveMigration()
            .build()
            .articleDao()

}

@Database(
    entities = [ArticleEntity::class],
    version = 2
)
abstract class ArticleDb : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
