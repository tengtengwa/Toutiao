package com.example.toutiao.ui.fragment.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toutiao.logic.model.AntipNewsModel
import com.example.toutiao.logic.model.TopNewsModel
import com.example.toutiao.logic.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AntipViewModel : ViewModel() {

    val antipNewsList = MutableLiveData<ArrayList<AntipNewsModel.AntipNewsList>>()

    fun refreshNews() = viewModelScope.launch {
        val list = NewsRepository.refreshAntipNews().newslist as ArrayList<AntipNewsModel.AntipNewsList>
        if (antipNewsList.value != null) {
            antipNewsList.value!!.addAll(0, list.subList(2, list.size - 1))
        } else {
            antipNewsList.value = list
        }
    }

    fun loadMore() = viewModelScope.launch {
        NewsRepository.refreshAntipNews().newslist as ArrayList<AntipNewsModel.AntipNewsList>
    }

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

