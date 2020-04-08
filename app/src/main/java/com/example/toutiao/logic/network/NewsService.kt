package com.example.toutiao.logic.network

import com.example.toutiao.logic.model.NewsModel
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("getQQNewsUnreadList?chlid=news_news_top&page=7&channelPosition=0&forward=0&picType=" +
            "0,0,0,0,0,2,2,0,1,0,1,0,2,0&last_id=20200405A0LRVF00&last_time=1586233951&user_chlid=" +
            "news_news_antip,news_news_xian,news_news_recommend,news_news_ent,news_news_sports," +
            "news_news_game,news_news_mil&lc_ids=TWF2020040700869200,20200406A0NC3400,20200406A03BEX00," +
            "SML2016111603351800&coldBootEnterChannelIDs=&coldBootFixChannelID=&hot_module_user_switch=0" +
            "&rtAd=1&new_user=0&devid=d7529871b98a83bd&qimei=fb06282b864e1778&uid=d7529871b98a83bd&" +
            "appver=29_android_6.0.71&trueVersion=6.0.71&omgid=8d4edbd67fe2b04c0e996e7bfc4bb44d90a8001021391c&" +
            "Cookie=lskey%3D;skey%3D;uin%3D; luin%3D;logintype%3D0; main_login%3D; " +
            "&qn-sig=7b8a39a571204c13497e404928368702&qn-rid=1002_3575c07b-b9ac-4ec4-9361-647518249bdb" +
            "&qn-newsig=79f9e5f6f3d8b650e6613ebd6400acb06d7b823ed48bb3e78798a24a365ae3e1")
    fun getTopNews(): Call<NewsModel>

    @GET("getQQNewsUnreadList?chlid=news_news_ent&page=7&channelPosition=5&forward=0&picType=" +
            "0,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1&last_id=20200407A07TJU00&last_time=1586242628" +
            "&user_chlid=news_news_antip,news_news_shan3xi,news_news_recommend,news_news_ent,news_" +
            "news_sports,news_news_game,news_news_mil&lc_ids=SML2016111603351800&coldBootEnterChannelIDs=&" +
            "coldBootFixChannelID=&hot_module_user_switch=0&rtAd=1&new_user=0&devid=d7529871b98a83bd&" +
            "qimei=fb06282b864e1778&uid=d7529871b98a83bd&appver=29_android_6.0.71&trueVersion=6.0.71&" +
            "omgid=8d4edbd67fe2b04c0e996e7bfc4bb44d90a8001021391c&Cookie=lskey%3D;skey%3D;uin%3D; " +
            "luin%3D;logintype%3D0; main_login%3D; &qn-sig=edd67d5a79580789f91e730c0ab55936&" +
            "qn-rid=1002_48dcacba-eeb5-48ec-8dcf-0a19ce806ebf&qn-newsig=26a1ed9912434d338cdd91f85c33" +
            "791d8f734223b49d820f1b56742ca83f32df")
    fun getEntNews(): Call<NewsModel>

    @GET("getQQNewsUnreadList?chlid=news_news_sports&page=2&channelPosition=6&forward=0&" +
            "picType=0,1,0,2,1,0,2,1,0,2,2,0,0,0,0,0,0,0,1,0&last_id=20200406A0KUOG00&last_time=1586243005&" +
            "user_chlid=news_news_antip,news_news_shan3xi,news_news_recommend,news_news_ent,news_news" +
            "_sports,news_news_game,news_news_mil&lc_ids=CELLSPO201305220000,SML2016111603351800&" +
            "coldBootEnterChannelIDs=&coldBootFixChannelID=&hot_module_user_switch=0&rtAd=1&new_user=0&" +
            "devid=d7529871b98a83bd&qimei=fb06282b864e1778&uid=d7529871b98a83bd&appver=29_android_6.0.71&" +
            "trueVersion=6.0.71&omgid=8d4edbd67fe2b04c0e996e7bfc4bb44d90a8001021391c&Cookie=lskey%3D;" +
            "skey%3D;uin%3D; luin%3D;logintype%3D0; main_login%3D; &qn-sig=0e897799f87700e53b1c33c6a53db5dc&" +
            "qn-rid=1002_6a7a2c04-f8fb-49ae-bcb4-6ab34bc6b62a&qn-newsig=7d12070ef476135fcf8417c8c7e3f" +
            "85a3ba885b73f44045d65f79bbeca951a34")
    fun getSportNews(): Call<NewsModel>

    @GET("getQQNewsUnreadList?chlid=news_news_antip&page=1&channelPosition=2&forward=0&picType" +
            "=0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,2&last_id=20200407V0327Q00&last_time=1586243770&" +
            "user_chlid=news_news_antip,news_news_shan3xi,news_news_recommend,news_news_ent,news_news" +
            "_sports,news_news_game,news_news_mil&lc_ids=CELL2020ANTIP000000,CELLANT201803060100&cold" +
            "BootEnterChannelIDs=&coldBootFixChannelID=&hot_module_user_switch=0&rtAd=1&new_user=0&" +
            "devid=d7529871b98a83bd&qimei=fb06282b864e1778&uid=d7529871b98a83bd&appver=29_android_6.0." +
            "71&trueVersion=6.0.71&omgid=8d4edbd67fe2b04c0e996e7bfc4bb44d90a8001021391c&Cookie=lskey%3D" +
            ";skey%3D;uin%3D; luin%3D;logintype%3D0; main_login%3D; &qn-sig=1bc58228fe60772ae06afaef43d3" +
            "a83c&qn-rid=1002_df232138-f739-4f01-ab71-e7a2e4bac5ec&qn-newsig=1ca08e3576088d7255f9039a8" +
            "30c1f423cb72170e364d3ba9bcbffe1977b093a")
    //世界疫情新闻
    fun getAntip(): Call<NewsModel>

}