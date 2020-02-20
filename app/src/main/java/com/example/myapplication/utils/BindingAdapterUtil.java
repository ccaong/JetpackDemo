package com.example.myapplication.utils;


import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

/**
 * @author : devel
 * @date : 2020/2/19 10:18
 * @desc :
 */
public class BindingAdapterUtil {

    @BindingAdapter("url")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

}
