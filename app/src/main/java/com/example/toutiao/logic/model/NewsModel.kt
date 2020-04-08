package com.example.toutiao.logic.model

import com.google.gson.annotations.SerializedName

data class NewsModel(val ret: Int, val ids: List<Id>, val newslist: List<NewsList>) {

    data class Id(val id: Int, val comments: Int)

    data class NewsList(val title: String, val time: String, val source: String, val url: String,
                        @SerializedName("thumbnails_qqnews") val images: List<String>,
                        val bigImage: List<String>, val video_channel: VideoInfo)

    data class VideoInfo(val video: Video)

    data class Video(val width: Int, val height: Int, val duration: String, val img: String,
                     val playcount: Int, val playurl: String)
}

