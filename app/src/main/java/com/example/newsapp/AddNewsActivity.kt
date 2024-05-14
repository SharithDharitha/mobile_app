package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newsapp.databinding.ActivityAddNewsBinding
import com.example.newsapp.databinding.ActivityMainBinding

class AddNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewsBinding
    private lateinit var db : NewsDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NewsDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val news = News(0, title, content)
            db.insertNews(news)
            finish()
            Toast.makeText(this, "News Saved", Toast.LENGTH_SHORT).show()
        }
    }
}