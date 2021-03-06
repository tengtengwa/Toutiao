package com.example.toutiao.logic.model

import com.google.gson.annotations.SerializedName

data class EntNewsModel(val ret: Int, val ids: List<Id>, val newslist: List<EntNewsList>) {

    data class Id(val id: String, val comments: Int)

    data class EntNewsList(val title: String, val time: String, val source: String, val url: String,
                           @SerializedName("thumbnails_qqnews") val image: List<String>,
                           val tmp3pic: List<String>,
                           @SerializedName("video_channel") val videoInfo: VideoInfo,
                           @SerializedName("ch1icon") val authorIcon: String)

    data class VideoInfo(val video: Video)

    data class Video(val width: Int, val height: Int, val duration: String, val img: String,
                     val playcount: Int, val playurl: String)
}