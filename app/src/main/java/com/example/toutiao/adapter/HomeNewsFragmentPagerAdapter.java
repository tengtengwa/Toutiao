package com.example.toutiao.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.toutiao.fragment.DomesticNewsFragment;
import com.example.toutiao.fragment.EttmNewsFragment;
import com.example.toutiao.fragment.SportsNewsFragment;

public class HomeNewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = {"头条", "娱乐", "体育"};

    public HomeNewsFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        DomesticNewsFragment fragmentDomestic = new DomesticNewsFragment();
        EttmNewsFragment fragmentInternational = new EttmNewsFragment();
        SportsNewsFragment fragmentSports = new SportsNewsFragment();
        if (position == 1) {
            return fragmentInternational;
        } else if (position == 2) {
            return fragmentSports;
        }
        return fragmentDomestic;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
