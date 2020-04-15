package com.example.toutiao.logic.repository

import com.example.toutiao.logic.dao.NewsDao
import com.example.toutiao.logic.model.*
import com.example.toutiao.logic.network.NewsNetwork

object NewsRepository {

    fun getTopNewsCache() = NewsDao.getTopNews()

    fun getEntNewsCache() = NewsDao.getEntNews()

    fun getSportNewsCache() = NewsDao.getSportNews()

    fun getAntipNewsCache() = NewsDao.getAntipNews()

    fun getVideoListCache() = NewsDao.getVideos()

    fun saveTopNews(list: ArrayList<TopNewsModel.TopNewsList>) = NewsDao.saveTopNews(list)

    fun saveEntNews(list: ArrayList<EntNewsModel.EntNewsList>) = NewsDao.saveEntNews(list)

    fun saveSportNews(list: ArrayList<SportNewsModel.SportNewsList>) = NewsDao.saveSportNews(list)

    fun saveAntipNews(list: ArrayList<AntipNewsModel.AntipNewsList>) = NewsDao.saveAntipNews(list)

    fun saveVideoList(list: ArrayList<VideoModel.VideoList>) = NewsDao.saveVideos(list)

    fun isTopNewsSaved() = NewsDao.isTopNewsSaved()

    fun isEntNewsSaved() = NewsDao.isEntNewsSaved()

    fun isSportNewsSaved() = NewsDao.isSportNewsSaved()

    fun isAntipNewsSaved() = NewsDao.isAntipNews()

    fun isVideoSaved() = NewsDao.isVideosSaved()

    suspend fun refreshTopNews() = NewsNetwork.getTopNewsList()

    suspend fun refreshAntipNews() = NewsNetwork.getAntipNewsList()

    suspend fun refreshEntNews() = NewsNetwork.getEntNewsList()

    suspend fun refreshSportNews() = NewsNetwork.getSportNewsList()

    suspend fun refreshVideo() = NewsNetwork.getVideoList()

/*    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) = liveData<Result<T>> {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }*/
}