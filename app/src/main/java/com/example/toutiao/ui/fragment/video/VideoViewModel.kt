package com.example.toutiao.ui.fragment.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toutiao.logic.model.VideoModel
import com.example.toutiao.logic.repository.NewsRepository

class VideoViewModel : ViewModel() {

    val videoList = MutableLiveData<ArrayList<VideoModel.VideoList>>()

    suspend fun refreshNews() = NewsRepository.refreshVideo().videoList as ArrayList<VideoModel.VideoList>

    suspend fun loadMore(): ArrayList<VideoModel.VideoList> {
        return NewsRepository.refreshVideo().videoList as ArrayList<VideoModel.VideoList>
    }

    fun saveNews() = videoList.value?.let { NewsRepository.saveVideoList(it) }

    fun isNewsSaved() = NewsRepository.isVideoSaved()

    fun getSavedNews() = NewsRepository.getVideoListCache()

    fun saveWhenExit() {
        val list = videoList.value
        if (list != null && list.size >= 20) {
            videoList.value?.removeAll(list.subList(0, list.size - 11))
            saveNews()
        }
    }
}