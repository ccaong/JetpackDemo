package com.example.myapplication.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FmPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> sTitle = new ArrayList<>();

    public FmPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<String> list) {
        super(fm);
        fragmentList = fragments;
        sTitle = list;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return sTitle.get(position);
    }
}
