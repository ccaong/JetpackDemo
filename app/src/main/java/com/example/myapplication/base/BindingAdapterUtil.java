package com.example.myapplication.base;


import android.os.Build;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.config.App;
import com.example.myapplication.R;
import com.example.myapplication.base.adapter.BasePagerAdapter;
import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.HomeBanner;
import com.example.myapplication.bean.responsebean.home.BannerData;
import com.example.myapplication.ui.adapter.BannerViewHolder;
import com.example.myapplication.util.CommonUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import jp.wasabeef.glide.transformations.BlurTransformation;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

/**
 * @author : devel
 * @date : 2020/2/19 10:18
 * @desc :
 */
public class BindingAdapterUtil {


    /**
     * 设置ViewPager的数据列表
     *
     * @param viewPager ViewPager
     * @param dataList  数据列表
     * @param <T>       数据类型
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter("dataList")
    public static <T> void setDataList(ViewPager viewPager, List<T> dataList) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter instanceof BasePagerAdapter) {
            BasePagerAdapter basePagerAdapter = (BasePagerAdapter) adapter;
            basePagerAdapter.setDataList(dataList);
        }
    }


    @BindingAdapter("int2String")
    public static void int2String(TextView textView, int i) {
        textView.setText("" + i);
    }

    @BindingAdapter("loadImage")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

    @BindingAdapter("loadImageWithError")
    public static void setImageUrlWithError(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_logo)
                .error(R.mipmap.ic_logo).fallback(R.mipmap.ic_logo)
                .transform(new CircleCrop());

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }


    @BindingAdapter("loadImageGoss")
    public static void setImageUrlWithGoss(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(imageView);
    }


    @BindingAdapter("visibility")
    public static void setViewVisibility(View view, Boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("bannerData")
    public static void setbBannerData(MZBannerView banner, BannerData bannerData) {
        if (bannerData == null || bannerData.getBannerData() == null) {
            return;
        }
        List<HomeBanner> movies = bannerData.getBannerData();
        banner.setPages(movies, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.start();
    }


    @BindingAdapter("isCollect")
    public static void setTodoGrade(ImageView view, boolean collect) {
        if (collect) {
            view.setImageResource(R.mipmap.ic_collect_yes);
        } else {
            view.setImageResource(R.mipmap.ic_collect_no);
        }
    }


    @BindingAdapter("articleTag")
    public static void setArticleTag(TextView view, ArticleBean articleBean) {
        if (articleBean != null && articleBean.getTags() != null) {
            if (articleBean.getTags().size() == 1) {
                view.setText(articleBean.getTags().get(0).getName());
            } else if (articleBean.getTags().size() >= 2) {
                String str = articleBean.getTags().get(0).getName() + articleBean.getTags().get(1).getName();
                view.setText(str);
            } else {
                view.setText("其他");
            }
        }
    }


    @BindingAdapter("todoGrade")
    public static void setTodoGrade(ImageView view, int priority) {
        if (priority == 0) {
            view.setImageResource(R.mipmap.ic_star);
        } else {
            view.setImageResource(R.mipmap.ic_star_outline);
        }
    }


    @BindingAdapter("todoCheck")
    public static void setToDoDone(CheckBox view, int status) {
        if (status == 0) {
            view.setChecked(false);
        } else {
            view.setChecked(true);
        }
    }


    @BindingAdapter("todoStatus")
    public static void setTodoStatus(TextView view, int status) {
        if (status == 0) {
            view.setText("未完成");
            view.setTextColor(App.getContext().getResources().getColor(R.color.app_color_theme_1));
        } else {
            view.setText("已完成");
            view.setTextColor(App.getContext().getResources().getColor(R.color.color_blue));
        }
    }

    @BindingAdapter("coinCount")
    public static void setCoinCount(TextView view, int count) {
        view.setText(count + "分");
    }

    @BindingAdapter("coinLeave")
    public static void setCoinLeave(TextView view, int count) {
        view.setText("LV" + count);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @BindingAdapter("coinRank")
    public static void setCoinRank(TextView view, int count) {
        if (count == 1) {
            view.setText("");
            view.setBackground(App.getContext().getDrawable(R.mipmap.ic_rank_first));
        } else if (count == 2) {
            view.setText("");
            view.setBackground(App.getContext().getDrawable(R.mipmap.ic_rank_second));

        } else if (count == 3) {
            view.setText("");
            view.setBackground(App.getContext().getDrawable(R.mipmap.ic_rank_thred));
        } else {
            view.setText("" + count);
            view.setBackground(null);
        }
    }


    /**
     * 设置VerticalTabLayout的标题
     *
     * @param verticalTabLayout VerticalTabLayout
     * @param mTitlesList       标题列表
     */
    @BindingAdapter("app:titleList")
    public static void setTitleList(VerticalTabLayout verticalTabLayout, List<String> mTitlesList) {
        if (CommonUtils.isListEmpty(mTitlesList)) {
            return;
        }

        verticalTabLayout.setTabAdapter(new SimpleTabAdapter() {

            @Override
            public int getCount() {
                return mTitlesList.size();
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent(mTitlesList.get(position))
                        .setTextColor(CommonUtils.getColor(R.color.colorPrimary),
                                CommonUtils.getColor(R.color.text_black_54))
                        .setTextSize(16)
                        .build();
            }
        });
    }

}
