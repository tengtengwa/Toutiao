package com.example.toutiao.ui.fragment.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toutiao.logic.model.TopNewsModel
import com.example.toutiao.logic.repository.NewsRepository

class TopNewsViewModel : ViewModel() {

    val topNewsList = MutableLiveData<ArrayList<TopNewsModel.TopNewsList>>()

    suspend fun refreshNews() = NewsRepository.refreshTopNews().newslist as ArrayList<TopNewsModel.TopNewsList>

    suspend fun loadMore(): ArrayList<TopNewsModel.TopNewsList> {
        return NewsRepository.refreshTopNews().newslist as ArrayList<TopNewsModel.TopNewsList>
    }

    fun saveNews() = topNewsList.value?.let { NewsRepository.saveTopNews(it) }

    fun isNewsSaved() = NewsRepository.isTopNewsSaved()

    fun getSavedNews() = NewsRepository.getTopNewsCache()

    fun saveWhenExit() {
        val list = topNewsList.value
        if (list != null) {
            topNewsList.value?.removeAll(list.subList(0, list.size - 11))
            saveNews()
        }
    }
}
