package com.example.newsapplication.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.models.Article


class NewsDescriptionFragment() : Fragment(R.layout.fragment_news_details) {

    private lateinit var articleImage: ImageView
    private lateinit var articleTitle: TextView
    private lateinit var articleProvider: TextView
    private lateinit var articleDescription: TextView

    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            article = bundle.getSerializable("article") as Article
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleImage = view.findViewById(R.id.article_image)
        articleTitle = view.findViewById(R.id.article_title)
        articleProvider = view.findViewById(R.id.article_provider)
        articleDescription = view.findViewById(R.id.article_description)

        articleTitle.text = article.title
        articleProvider.text = article.source.name
        articleDescription.text = article.description
        articleImage.let {
            context?.let { it1 ->
                Glide.with(it1).load(article.urlToImage).into(it)
            }
        }

        val fullArticleButton: Button = view.findViewById(R.id.full_article_button)

        fullArticleButton.setOnClickListener {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse(article.url)
            )
            startActivity(viewIntent)
        }
    }
}