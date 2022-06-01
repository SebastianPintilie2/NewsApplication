package com.example.newsapplication.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.models.NewsResponse
import com.example.newsapplication.repository.NewsRepository
import com.example.newsapplication.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val news: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        news.postValue(Resource.Loading())
        val response = newsRepository.getNews()
        news.postValue(handleNewsResponse(response))
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}
