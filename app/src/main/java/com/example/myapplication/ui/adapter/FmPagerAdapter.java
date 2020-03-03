package com.example.myapplication.ui.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author devel
 */
public class FmPagerAdapter extends FragmentPagerAdapter {

    private Fragment mCurrentFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> sTitle = new ArrayList<>();

    public FmPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        fragmentList = fragments;
    }

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
        if (sTitle != null && sTitle.size() != 0) {
            return sTitle.get(position);
        } else {
            return null;
        }
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }


    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
