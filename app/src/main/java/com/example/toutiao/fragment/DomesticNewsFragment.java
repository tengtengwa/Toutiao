package com.example.toutiao.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toutiao.R;
import com.example.toutiao.Utils.RequestWithOkhttp;
import com.example.toutiao.activity.MyApplication;
import com.example.toutiao.adapter.Tab1RecyclerViewAdapter;
import com.example.toutiao.bean.News;
import com.example.toutiao.Base.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class DomesticNewsFragment extends BaseFragment {

    private ArrayList<News> headNewsList = new ArrayList<>();
    private Tab1RecyclerViewAdapter adapter;

    @BindView(R.id.domes_srl)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.rv1_news)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.domesticnews_fragment, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("headNews", 0);
        String newsData = sp.getString("headNewsData", "");
//        Log.d("ttw", "newsData: " + newsData);
        if ("".equals(newsData)) {
            try {
                initView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Gson gson = new Gson();
            Log.d("ttw", "news1: " + newsData);
            headNewsList = gson.fromJson(newsData, new TypeToken<ArrayList<News>>() {
            }.getType());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (headNewsList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getContext());
                        adapter = new Tab1RecyclerViewAdapter(headNewsList, getActivity());
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
        refreshLayout.setDragRate(0.6f);
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ConnectivityManager manager = (ConnectivityManager) Objects.requireNonNull(getContext())
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = Objects.requireNonNull(manager).getActiveNetworkInfo();
                if (networkInfo != null) {
                    //下拉刷新，加载更多（待完成）
                    initView();
                    refreshLayout.finishRefresh(1500);
                } else {
                    Toast.makeText(getContext(), "网络不给力，请检查网络设置", Toast.LENGTH_LONG).show();
                    refreshLayout.finishRefresh(500);
                }
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉刷新，待完成
//                adapter.loadMore(headNewsList);
                refreshLayout.finishRefresh(1500);
                refreshLayout.finishLoadMore(1500);
            }
        });
        return view;
    }

    private void initView() {
        //请求头条新闻
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response;
                try {
                    response = RequestWithOkhttp.sendOkhttpRequest
                            (new URL("http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html"));
                    if (response != null) {
                        ParseWithJsonObject(response);
                    } else {
                        Toast.makeText(MyApplication.getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }).start();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (headNewsList.size() > 0) {
                    LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getContext());
                    adapter = new Tab1RecyclerViewAdapter(headNewsList, getActivity());
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    private void ParseWithJsonObject(Response response) throws Exception {
        JSONObject jsonObject = new JSONObject(response.body().string());       //此处是string()方法
        JSONArray jsonArray = jsonObject.getJSONArray("T1348647853363");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            News news1 = new News();
            news1.setmTitle(object.getString("title"));
            news1.setmImage(object.getString("imgsrc"));
            news1.setmSource(object.getString("source"));
            news1.setmTime(object.getString("mtime").split(" ")[1]);
            try {
                String url = object.getString("url");
                news1.setmUrl(url);
            } catch (Exception ignored) {

            }
            headNewsList.add(0, news1);
        }
        SharedPreferences sp = Objects.requireNonNull(getContext()).getSharedPreferences("headNews", 0);
        Gson gson = new Gson();
        String newsData = gson.toJson(headNewsList);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("headNewsData", newsData);
        Log.d("ttw", "json list2: ");
        editor.apply();
    }
}
