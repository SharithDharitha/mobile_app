package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newsapp.databinding.ActivityAddNewsBinding
import com.example.newsapp.databinding.ActivityUpdateNewsBinding

class UpdateNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNewsBinding
    private lateinit var db: NewsDatabaseHelper
    private var newsId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NewsDatabaseHelper(this)

        newsId = intent.getIntExtra("news_id", -1)
        if (newsId==-1) {
            finish()
            return
        }

        val news = db.getNewsbyID(newsId)
        binding.updateTitleEditText.setText(news.title)
        binding.updateContentEditText.setText(news.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updatedNews = News(newsId, newTitle, newContent)
            db.updateNews(updatedNews)
            finish()
            Toast.makeText(this, "News Updated Successfully", Toast.LENGTH_SHORT).show()

        }
    }
}