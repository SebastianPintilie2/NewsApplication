package com.example.newsapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.models.Article
import com.example.newsapplication.utils.toDate
import com.example.newsapplication.utils.untilNow
import org.w3c.dom.Text
import java.util.*

class RecyclerViewAdapter(val listener: RecyclerviewOnClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder>() {

    private val FIRST_ITEM_TYPE = 0;
    private val OTHER_ITEM_TYPE = 1;

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        if(viewType == FIRST_ITEM_TYPE){
            return NewsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_first_item_view,
                    parent,
                    false
                )
            )
        } else {
            return NewsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recyclerview_item_view,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       if(holder.itemViewType == FIRST_ITEM_TYPE){
           val article = differ.currentList[position]
           holder.firstImage?.let { Glide.with(holder.itemView.context).load(article.urlToImage).into(it) }
           holder.firstTitle?.text = article.title
           holder.firstHeadline?.text = article.publishedAt.toDate()?.untilNow(Date())
           holder.itemView.setOnClickListener {
               listener.recyclerviewClick(position, article)
           }
       } else if(holder.itemViewType == OTHER_ITEM_TYPE){
           val article = differ.currentList[position]
           holder.articleImage?.let { Glide.with(holder.itemView.context).load(article.urlToImage).into(it) }
           holder.title?.text = article.title
           holder.headline?.text = article.publishedAt.toDate()?.untilNow(Date())
           holder.itemView.setOnClickListener {
               listener.recyclerviewClick(position, article)
           }
       }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if(position == 0){
            FIRST_ITEM_TYPE
        } else OTHER_ITEM_TYPE
    }

    open inner class NewsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val headline: TextView? = v.findViewById(R.id.upload_time_text)
        val title: TextView? = v.findViewById(R.id.article_title_text)
        val articleImage: ImageView? = v.findViewById(R.id.article_image)
        val firstHeadline: TextView? = v.findViewById(R.id.article_time_first)
        val firstTitle: TextView? = v.findViewById(R.id.article_title_first)
        val firstImage: ImageView? = v.findViewById(R.id.article_image_first)
    }

    interface RecyclerviewOnClickListener {
        fun recyclerviewClick(position: Int, article: Article)
    }
}
