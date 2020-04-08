package com.example.toutiao.logic.network

import com.example.toutiao.logic.model.NewsModel
import com.example.toutiao.utils.await

object NewsNetwork {

    private val newsService = ServiceCreator.create("https://r.inews.qq.com/", NewsService::class.java)

    suspend fun getTopNewsList(): List<NewsModel.NewsList> = newsService.getTopNews().await().newslist

    suspend fun getEntNewsList(): List<NewsModel.NewsList> = newsService.getEntNews().await().newslist

    suspend fun getSportNewsList(): List<NewsModel.NewsList> = newsService.getSportNews().await().newslist

    suspend fun getAntipNewsList():List<NewsModel.NewsList> = newsService.getAntip().await().newslist

}