package com.example.myapplication.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.entity.HomeBanner;
import com.example.myapplication.entity.HomeBannerEntity;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MZBannerView mMZBanner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mMZBanner = root.findViewById(R.id.banner);

        homeViewModel.loadDataByNetWork();

        homeViewModel.getBanner().observe(this, new Observer<HomeBannerEntity>() {
            @Override
            public void onChanged(HomeBannerEntity homeBannerEntity) {
                setBanner(homeBannerEntity.getData());
            }
        });
        return root;
    }

    private void setBanner(List<HomeBanner> movies) {
        mMZBanner.setPages(movies, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mMZBanner.start();
    }


    public static class BannerViewHolder implements MZViewHolder<HomeBanner> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.remote_banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.remote_item_image);
            return view;
        }

        @Override
        public void onBind(Context context, int i, HomeBanner homeBanner) {
            Glide.with(context).load(homeBanner.getImagePath()).into(mImageView);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMZBanner.pause();
    }

}