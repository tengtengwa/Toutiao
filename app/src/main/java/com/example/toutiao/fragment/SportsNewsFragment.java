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
import com.example.toutiao.adapter.Tab3RecyclerViewAdapter;
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

public class SportsNewsFragment extends BaseFragment {

    private ArrayList<News> sportsNewsList = new ArrayList<>();
    private Tab3RecyclerViewAdapter adapter;

    @BindView(R.id.sports_srl)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.rv3_news)
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sportsnews_fragment, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("sportsNews", 0);
        String newsData = sp.getString("sportsNewsData", "");
        if ("".equals(newsData)) {
            initView();
        } else {
            Gson gson = new Gson();
            Log.d("ttw", "news3: " + newsData);
            sportsNewsList = gson.fromJson(newsData, new TypeToken<ArrayList<News>>() {}.getType());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sportsNewsList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        adapter = new Tab3RecyclerViewAdapter(sportsNewsList, getActivity());
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
                ConnectivityManager manager = (ConnectivityManager) getContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
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
//                adapter.loadMore(sportsNewsList);
                refreshLayout.finishRefresh(1500);
                refreshLayout.finishLoadMore(1500);
            }
        });
        return view;
    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = RequestWithOkhttp.sendOkhttpRequest(
                            new URL("http://c.3g.163.com/nc/article/list/T1348649079062/0-20.html"));
                    if (response != null) {
                        ParseWithJSONObject(response);
                    } else {
                        Toast.makeText(MyApplication.getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
                            adapter = new Tab3RecyclerViewAdapter(sportsNewsList, getActivity());
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }

            }
        }).start();
    }

    private void ParseWithJSONObject(Response response) throws Exception {
        String data = response.body().string();
        JSONObject jsonObject = new JSONObject(data);
        JSONArray array = jsonObject.getJSONArray("T1348649079062");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            News news = new News();
            try {
                String url = object.getString("url");
                news.setmUrl(url);
            } catch (Exception e) {

            }
            news.setmSource(object.getString("source"));
            news.setmImage(object.getString("imgsrc"));
            news.setmTime(object.getString("mtime").split(" ")[1]);
            news.setmTitle(object.getString("title"));
            sportsNewsList.add(0, news);
        }
        SharedPreferences.Editor editor = Objects.requireNonNull(getContext())
                .getSharedPreferences("sportsNews", 0).edit();
        Gson gson = new Gson();
        String newsData = gson.toJson(sportsNewsList);
        editor.putString("sportsNewsData", newsData);
        Log.d("ttw", "json list3: ");
        editor.apply();

//        for (int i = 0; i < sportsNewsList.size(); i++) {
//            Log.d("ttw", "体育新闻：" + sportsNewsList.get(i).getmTitle());
//        }
    }
}
