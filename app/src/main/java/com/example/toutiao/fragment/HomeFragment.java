package com.example.toutiao.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.toutiao.Base.BaseFragment;
import com.example.toutiao.R;
import com.example.toutiao.activity.MyApplication;
import com.example.toutiao.activity.NewsDetailsActivity;
import com.example.toutiao.adapter.HomeNewsFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tv_search)
    TextView searchText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, container, false);
        ButterKnife.bind(this, view);

        //判断是否第一次启动
        SharedPreferences sp = Objects.requireNonNull(getActivity())
                .getSharedPreferences("CheckFirst", 0);
        SharedPreferences.Editor editor = sp.edit();
        boolean isFirst = sp.getBoolean("isFirst", true);
        if (isFirst) {
            editor.putBoolean("isFirst", false);
            editor.apply();
        }
        initViews();
        return view;
    }

    private void initViews() {
        searchText.setOnClickListener(this);

        assert getFragmentManager() != null;
        HomeNewsFragmentPagerAdapter adapter = new HomeNewsFragmentPagerAdapter(getChildFragmentManager(), 0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent(MyApplication.getContext(), NewsDetailsActivity.class);
                intent.putExtra("newsUrl", "https://www.baidu.com");
                startActivity(intent);
                break;

        }
    }
}
