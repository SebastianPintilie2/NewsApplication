package com.example.newsapplication.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.R
import com.example.newsapplication.database.ArticleDatabase
import com.example.newsapplication.repository.NewsRepository


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    var newsFragment = NewsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        showFragment(newsFragment)
    }

    fun showFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragment, fragment)
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }
}