package com.example.toutiao.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.toutiao.Contact.BaseContact;
import com.example.toutiao.R;
import com.example.toutiao.Base.BaseActivity;
import com.example.toutiao.fragment.HomeFragment;
import com.example.toutiao.fragment.VideoFragment;
import com.example.toutiao.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<BaseContact.IPresenter> implements BaseContact.IView {

    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initLayout(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorTintBlue));
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container, new HomeFragment())
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_home:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.activity_main_fragment_container, new HomeFragment())
                                .commit();
                        return true;
                    case R.id.item_video:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.activity_main_fragment_container, new VideoFragment())
                                .commit();
                        return true;
                    case R.id.item_profile:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.activity_main_fragment_container, new ProfileFragment())
                                .commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BaseContact.IPresenter setPresenter() {
        return null;
    }
}
