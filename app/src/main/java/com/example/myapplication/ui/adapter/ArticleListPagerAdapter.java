package com.example.myapplication.ui.adapter;

import com.example.myapplication.base.adapter.BasePagerAdapter;
import com.example.myapplication.entity.WeChatBean;
import com.example.myapplication.ui.wechat.WeChatContentListFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * 微信公众号列表PagerAdapter
 *
 * @author devel
 */
public class ArticleListPagerAdapter extends BasePagerAdapter<WeChatBean> {

    public ArticleListPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentMap.get(position);
        if (fragment == null) {
            WeChatBean chapter = mDataList.get(position);
            fragment = WeChatContentListFragment.newInstance(chapter.getId());
            mFragmentMap.put(position, fragment);
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        WeChatBean chapter = mDataList.get(position);
        return chapter.getName();
    }
}
