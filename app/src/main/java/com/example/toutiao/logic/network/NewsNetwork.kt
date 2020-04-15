package com.example.toutiao.logic.network

import com.example.toutiao.logic.model.*
import com.example.toutiao.utils.await

object NewsNetwork {

    private val newsService = ServiceCreator.create("https://r.inews.qq.com/", NewsService::class.java)

    suspend fun getTopNewsList() = newsService.getTopNews().await()

    suspend fun getEntNewsList() = newsService.getEntNews().await()

    suspend fun getSportNewsList() = newsService.getSportNews().await()

    suspend fun getAntipNewsList() = newsService.getAntip().await()

    suspend fun getVideoList() = newsService.getVideoInfo().await()

}