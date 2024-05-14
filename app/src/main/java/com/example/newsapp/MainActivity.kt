package com.example.newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NewsDatabaseHelper
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NewsDatabaseHelper(this)
        newsAdapter = NewsAdapter(db.getAllNews(), this)

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.newsRecyclerView.adapter = newsAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddNewsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        newsAdapter.refreshData(db.getAllNews())
    }
}