package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter (private var news: List<News>, context: Context) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val db: NewsDatabaseHelper = NewsDatabaseHelper(context)

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView : TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton : ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = news[position]
        holder.titleTextView.text = news.title
        holder.contentTextView.text = news.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNewsActivity::class.java).apply {
                putExtra("news_id", news.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteNews(news.id)
            refreshData(db.getAllNews())
            Toast.makeText(holder.itemView.context,"Note Deleted Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData (newNews: List<News>){
        news = newNews
        notifyDataSetChanged()
    }
}