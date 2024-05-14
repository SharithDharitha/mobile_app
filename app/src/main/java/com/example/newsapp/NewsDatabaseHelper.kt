package com.example.newsapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.core.content.contentValuesOf

class NewsDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "newsapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnews"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNews(news: News){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, news.title)
            put(COLUMN_CONTENT, news.content)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllNews(): List<News> {
        val newsList = mutableListOf<News>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val news = News (id, title, content)
            newsList.add(news)
        }
        cursor.close()
        db.close()
        return newsList
    }

    fun updateNews(news: News){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, news.title)
            put(COLUMN_CONTENT, news.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(news.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getNewsbyID(newsId : Int): News{
        val db = readableDatabase
        val query ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $newsId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return News(id, title, content)
    }

    fun deleteNews(newsId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(newsId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }


}