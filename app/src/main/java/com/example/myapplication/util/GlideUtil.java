package com.example.myapplication.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.App;
import com.example.myapplication.R;

/**
 * @author : devel
 * @date : 2020/2/28 16:50
 * @desc :
 */
public class GlideUtil {


    public static void loadImageWithDefault(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_logo)
                .error(R.mipmap.ic_logo).fallback(R.mipmap.ic_logo)
                .transform(new CircleCrop());

        Glide.with(App.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
