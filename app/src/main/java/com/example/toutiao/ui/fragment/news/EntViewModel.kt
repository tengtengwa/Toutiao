package com.example.toutiao.ui.fragment.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toutiao.logic.model.EntNewsModel
import com.example.toutiao.logic.repository.NewsRepository

class EntViewModel : ViewModel() {

    val entNewsList = MutableLiveData<ArrayList<EntNewsModel.EntNewsList>>()

    suspend fun refreshNews() = NewsRepository.refreshEntNews().newslist as ArrayList<EntNewsModel.EntNewsList>

    suspend fun loadMore(): ArrayList<EntNewsModel.EntNewsList>
            = NewsRepository.refreshEntNews().newslist as ArrayList<EntNewsModel.EntNewsList>

    fun saveNews() = entNewsList.value?.let { NewsRepository.saveEntNews(it) }

    fun isNewsSaved() = NewsRepository.isEntNewsSaved()

    fun getSavedNews() = NewsRepository.getEntNewsCache()

    fun saveWhenExit() {
        val list = entNewsList.value
        if (list != null && list.size >= 20) {
            entNewsList.value?.removeAll(list.subList(0, list.size - 11))
            saveNews()
        }
    }
}
