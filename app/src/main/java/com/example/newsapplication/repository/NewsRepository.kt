package com.example.newsapplication.repository

import com.example.newsapplication.api.RetrofitInstance
import com.example.newsapplication.database.ArticleDatabase

class NewsRepository (
    val db: ArticleDatabase
){
suspend fun getNews() =
    RetrofitInstance.api.getNews()
}