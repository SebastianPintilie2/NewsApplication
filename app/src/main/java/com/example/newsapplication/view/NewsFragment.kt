package com.example.newsapplication.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.adapters.RecyclerViewAdapter
import com.example.newsapplication.models.Article
import com.example.newsapplication.utils.Resource

class NewsFragment: Fragment(R.layout.fragment_news), RecyclerViewAdapter.RecyclerviewOnClickListener {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: RecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    val newsDescriptionFragment = NewsDescriptionFragment()

    val TAG = "NewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        recyclerView = view.findViewById(R.id.news_recyclerview)
        setupRecyclerView()

        viewModel.news.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    it.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Log.e(TAG, message)
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
    }

    private fun setupRecyclerView(){
        newsAdapter = RecyclerViewAdapter(this)
        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    fun showFragment(fragment: Fragment, article: Article) {
        val bundle = Bundle()
        bundle.putSerializable("article", article)
        fragment.arguments = bundle
        val fragmentManager = parentFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragment, fragment)
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }

    override fun recyclerviewClick(position: Int, article: Article) {
        showFragment(newsDescriptionFragment, article)
    }
}