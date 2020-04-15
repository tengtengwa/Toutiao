package com.example.toutiao.ui.fragment.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toutiao.logic.model.SportNewsModel
import com.example.toutiao.logic.repository.NewsRepository

class SportViewModel : ViewModel() {

    val sportNewsList = MutableLiveData<ArrayList<SportNewsModel.SportNewsList>>()

    suspend fun refreshNews() = NewsRepository.refreshSportNews().newslist as ArrayList<SportNewsModel.SportNewsList>

    suspend fun loadMore(): ArrayList<SportNewsModel.SportNewsList>
            = NewsRepository.refreshSportNews().newslist as ArrayList<SportNewsModel.SportNewsList>

    fun saveNews() = sportNewsList.value?.let { NewsRepository.saveSportNews(it) }

    fun isNewsSaved() = NewsRepository.isSportNewsSaved()

    fun getSavedNews() = NewsRepository.getSportNewsCache()

    fun saveWhenExit() {
        val list = sportNewsList.value
        if (list != null) {
            sportNewsList.value?.removeAll(list.subList(0, list.size - 11))
            saveNews()
        }
    }
}
