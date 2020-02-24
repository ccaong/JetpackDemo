package com.example.myapplication.ui.adapter;

import android.view.ViewGroup;

import com.example.myapplication.base.adapter.BaseAdapter;
import com.example.myapplication.base.adapter.MyObserver;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.ui.wechat.wxcontent.ArticleViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 文章列表Adapter
 * @author devel
 */
public class ArticleListAdapter extends BaseAdapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ARTICLE = 1;
    private static final int VIEW_TYPE_BANNER = 2;

    private LifecycleRegistry mLifecycleRegistry=new LifecycleRegistry(ArticleListAdapter.this);


    public ArticleListAdapter(@NonNull LiveData<List<Object>> dataList) {
        super(dataList);

        MyObserver myObserver = new MyObserver();
        mLifecycleRegistry.addObserver(myObserver);

    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ARTICLE:
                return new ArticleViewHolder(parent);

            case VIEW_TYPE_BANNER:
//                return new BannerViewHolder();

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object data = mDataList.getValue().get(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_ARTICLE:
                ((ArticleViewHolder) holder).getViewModel().setBaseModel((ArticleBean) data);
                break;

            case VIEW_TYPE_BANNER:
//                ((BannerViewHolder) holder).getViewModel().setBaseModel((BannerData) data);
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object data = mDataList.getValue().get(position);
        if (data instanceof ArticleBean) {
            return VIEW_TYPE_ARTICLE;
        } /*else if (data instanceof BannerData) {
            return VIEW_TYPE_BANNER;
        }*/ else {
            return super.getItemViewType(position);
        }
    }
}
