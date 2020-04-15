package com.example.toutiao.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.toutiao.base.BaseApplication
import com.example.toutiao.logic.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NewsDao {

    private fun sharedPreferences() =
            BaseApplication.context.getSharedPreferences("weather", Context.MODE_PRIVATE)

    fun saveTopNews(topNewsList: ArrayList<TopNewsModel.TopNewsList>) {
        sharedPreferences().edit {
            putString("topNewsList", Gson().toJson(topNewsList,
                    object : TypeToken<ArrayList<TopNewsModel.TopNewsList>>() {}.type))
        }
    }
    fun getTopNews(): ArrayList<TopNewsModel.TopNewsList> {
        val topNewsList = sharedPreferences().getString("topNewsList", "")
        return Gson().fromJson(topNewsList, object : TypeToken<ArrayList<TopNewsModel.TopNewsList>>() {}.type)
    }
    fun isTopNewsSaved() = sharedPreferences().contains("topNewsList")

    fun saveAntipNews(antipNewsList: ArrayList<AntipNewsModel.AntipNewsList>) {
        sharedPreferences().edit {
            putString("antipNewsList", Gson().toJson(antipNewsList,
                    object : TypeToken<ArrayList<AntipNewsModel.AntipNewsList>>() {}.type))
        }
    }
    fun getAntipNews(): ArrayList<AntipNewsModel.AntipNewsList> {
        val antipNewsList = sharedPreferences().getString("antipNewsList", "")
        return Gson().fromJson(antipNewsList, object : TypeToken<ArrayList<AntipNewsModel.AntipNewsList>>() {}.type)
    }
    fun isAntipNews() = sharedPreferences().contains("antipNewsList")

    fun saveEntNews(entNewsList: ArrayList<EntNewsModel.EntNewsList>) {
        sharedPreferences().edit {
            putString("entNewsList", Gson().toJson(entNewsList,
                    object : TypeToken<ArrayList<EntNewsModel.EntNewsList>>() {}.type))
        }
    }
    fun getEntNews(): ArrayList<EntNewsModel.EntNewsList> {
        val entNewsList = sharedPreferences().getString("entNewsList", "")
        return Gson().fromJson(entNewsList, object : TypeToken<ArrayList<EntNewsModel.EntNewsList>>() {}.type)
    }
    fun isEntNewsSaved() = sharedPreferences().contains("entNewsList")

    fun saveSportNews(sportNewsList: ArrayList<SportNewsModel.SportNewsList>) {
        sharedPreferences().edit {
            putString("sportNewsList", Gson().toJson(sportNewsList,
                    object : TypeToken<ArrayList<SportNewsModel.SportNewsList>>() {}.type))
        }
    }

    fun getSportNews(): ArrayList<SportNewsModel.SportNewsList> {
        val sportNewsList = sharedPreferences().getString("sportNewsList", "")
        return Gson().fromJson(sportNewsList,
                object : TypeToken<ArrayList<SportNewsModel.SportNewsList>>() {}.type)
    }

    fun isSportNewsSaved() = sharedPreferences().contains("sportNewsList")

    fun saveVideos(videoList: ArrayList<VideoModel.VideoList>) {
        sharedPreferences().edit {
            putString("videoList", Gson().toJson(videoList,
                    object : TypeToken<ArrayList<VideoModel.VideoList>>() {}.type))
        }
    }

    fun getVideos(): ArrayList<VideoModel.VideoList> {
        val videoList = sharedPreferences().getString("videoList", "")
        return Gson().fromJson(videoList,
                object : TypeToken<ArrayList<VideoModel.VideoList>>() {}.type)
    }

    fun isVideosSaved() = sharedPreferences().contains("videoList")
}