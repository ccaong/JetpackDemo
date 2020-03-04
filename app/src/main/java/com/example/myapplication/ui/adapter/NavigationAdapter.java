package com.example.myapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.NavigationBean;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author devel
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.SystemViewHolder> {

    public List<NavigationBean> navigationBeanList;

    public NavigationAdapter(List<NavigationBean> list) {
        this.navigationBeanList = list;
    }

    /**
     * 改变数据
     *
     * @param newItemDatas
     */
    public void onItemDatasChanged(List<NavigationBean> newItemDatas) {
        this.navigationBeanList = newItemDatas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SystemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.item_navigation_layout, parent, false);
        return new SystemViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SystemViewHolder holder, int position) {

        NavigationBean bean = navigationBeanList.get(position);
        //绑定数据
        holder.binding.setVariable(BR.navigationBean, bean);
        showTagView(holder.binding.getRoot().findViewById(R.id.children_view), bean.getArticles(), holder);
        //防止数据闪烁
        holder.binding.executePendingBindings();
    }


    private void showTagView(RecyclerView recyclerView, final List<ArticleBean> beanList, SystemViewHolder holder) {

        FlexboxLayoutManager manager = new FlexboxLayoutManager(holder.binding.getRoot().getContext());
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setFlexWrap(FlexWrap.WRAP);
        recyclerView.setLayoutManager(manager);

        CommonAdapter<ArticleBean> commonAdapter =
                new CommonAdapter<ArticleBean>(beanList, R.layout.item_navigation_tag_layout, BR.navigationChildren) {
                    @Override
                    public void addListener(View root, ArticleBean itemData, int position) {
                        root.findViewById(R.id.tv_tag).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addChildrenListener(root, itemData, position);
                            }
                        });
                    }
                };
        recyclerView.setAdapter(commonAdapter);
    }


    /**
     * 添加监听回调
     *
     * @param root
     * @param itemData
     * @param position
     */
    public void addListener(View root, NavigationBean itemData, int position) {
    }

    /**
     * 添加监听回调
     *
     * @param root
     * @param itemData
     */
    public void addChildrenListener(View root, ArticleBean itemData, int position) {
    }

    @Override
    public int getItemCount() {
        return navigationBeanList != null ? navigationBeanList.size() : 0;
    }


    public class SystemViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public SystemViewHolder(@NonNull ViewDataBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
