package com.example.toutiao.ui.fragment.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toutiao.logic.model.AntipNewsModel
import com.example.toutiao.logic.model.TopNewsModel
import com.example.toutiao.logic.repository.NewsRepository

class AntipViewModel : ViewModel() {

    val antipNewsList = MutableLiveData<ArrayList<AntipNewsModel.AntipNewsList>>()

    suspend fun refreshNews() = NewsRepository.refreshAntipNews().newslist as ArrayList<AntipNewsModel.AntipNewsList>

    suspend fun loadMore(): ArrayList<AntipNewsModel.AntipNewsList>
            = NewsRepository.refreshAntipNews().newslist as ArrayList<AntipNewsModel.AntipNewsList>

    fun saveNews() = antipNewsList.value?.let { NewsRepository.saveAntipNews(it) }

    fun isNewsSaved() = NewsRepository.isAntipNewsSaved()

    fun getSavedNews() = NewsRepository.getAntipNewsCache()

    fun saveWhenExit() {
        val list = antipNewsList.value
        if (list != null) {
            antipNewsList.value?.removeAll(list.subList(0, list.size - 11))
            saveNews()
        }
    }

}
